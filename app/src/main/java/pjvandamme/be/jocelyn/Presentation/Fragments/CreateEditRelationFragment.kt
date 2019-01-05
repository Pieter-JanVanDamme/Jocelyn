package pjvandamme.be.jocelyn.Presentation.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import pjvandamme.be.jocelyn.Data.Persistence.RelationDao
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.ViewModels.CreateEditRelationViewModel
import pjvandamme.be.jocelyn.Domain.ViewModels.CreateEditRelationViewModelFactory
import pjvandamme.be.jocelyn.R
import java.util.*


class CreateEditRelationFragment : Fragment() {

    private lateinit var viewModel: CreateEditRelationViewModel
    var fragmentMode: CreateEditRelationFragmentMode? = null
    var receivedRelationAttributes: Boolean = false

    private val maxNameLength = 40
    private val maxMonikerLength = 20
    private val maxNutshellLength = 175
    private var currentRelationId: Long? = null

    lateinit var titleText: TextView
    lateinit var nameText: EditText
    lateinit var monikerText: EditText
    lateinit var nutshellText: EditText

    lateinit var nameError: TextView
    lateinit var monikerError: TextView
    lateinit var nutshellError: TextView

    companion object {
        fun newInstance() = CreateEditRelationFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders
            .of(this, CreateEditRelationViewModelFactory(activity?.application!!, null))
            .get(CreateEditRelationViewModel::class.java)


        // Determine whether this fragment should be in CREATE mode or in EDIT mode
        var mode: String? = arguments?.getString("mode")
        if(mode != null && mode.equals(CreateEditRelationFragmentMode.UPDATE.mode)){
            fragmentMode = CreateEditRelationFragmentMode.UPDATE
        }
        // We assume CREATE mode by default
        else {
            fragmentMode = CreateEditRelationFragmentMode.CREATE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_edit_relation_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val discard: ImageView = view!!.findViewById(R.id.discardImage)
        discard.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        /* INITIALIZE VIEW ELEMENTS */
        titleText = view!!.findViewById(R.id.createUpdateFragmentTitle)
        nameText = view!!.findViewById(R.id.editName)
        monikerText = view!!.findViewById(R.id.editMoniker)
        nutshellText= view!!.findViewById(R.id.editNutshell)
        nameError = view!!.findViewById(R.id.nameError)
        monikerError = view!!.findViewById(R.id.monikerError)
        nutshellError = view!!.findViewById(R.id.nutshellError)

        // make errorTexts invisible initially
        for(viewText in listOf(nameError, monikerError, nutshellError)){
            hideError(viewText)
        }

        // Setup in case EDIT MODE
        if(fragmentMode == CreateEditRelationFragmentMode.UPDATE) {
            // get the relationId, use it to change the state of our ViewModel so we may fetch the correct data
            var relationId: Long? = arguments?.getLong("relationId")
            if (relationId != null) {
                viewModel.setRelationInFragment(relationId)
                viewModel.relationInFragment?.observe(this, object: Observer<Relation>{
                    override fun onChanged(r: Relation?){
                        // prevent changes to EditText elements while the user is typing
                        if(!receivedRelationAttributes) {
                            /* currentRelationId must be set BEFORE the textChangeListener of monikerText, if not
                               we risk immediately getting the error thrown  */
                            currentRelationId = r?.relationId
                            nameText.setText(r?.fullName)
                            monikerText.setText(r?.currentMoniker)
                            nutshellText.setText(r?.nutshell)
                            receivedRelationAttributes = true
                        }
                    }
                })

                val submitButton: Button = view!!.findViewById(R.id.submitButton)
                submitButton.setOnClickListener{
                        try {
                            validateFullName(nameText.editableText.toString())
                            validateMoniker(monikerText.editableText.toString())
                            validateNutshell(nutshellText.editableText.toString())
                            // if none of the trivial validations fail, we will validate that the chosen moniker is
                            // unique and, if it is, update the Relation in the database
                            validateMonikerUniqueSubmit(monikerText.editableText.toString())
                        } catch (e: InputMismatchException) {
                            /* do nothing, error text was already set in onTextChanged listener(s) */
                        }
                }
            }
            titleText.setText(R.string.update_relation_fragment_title)
        }

        // Setup in case CREATE MODE
        if(fragmentMode == CreateEditRelationFragmentMode.CREATE){
            titleText.setText(R.string.create_relation_fragment_title)
            // we don't need to receive relation attributes
            receivedRelationAttributes = true
        }

        // setup nameText textChangedListener to validate input
        nameText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                try{
                    validateFullName(editable.toString())
                    hideError(nameError)
                } catch(e: InputMismatchException){
                    showError(nameError, if(e.message.isNullOrBlank()) "" else e.message!!)
                }
            }

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) { /* nada */ }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) { /* ditto */ }
        })

        // setup nameText textChangedListener to validate input
        monikerText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                try{
                    validateMoniker(editable.toString())
                    hideError(monikerError)
                } catch(e: InputMismatchException){
                    showError(monikerError, if(e.message.isNullOrBlank()) "" else e.message!!)
                }
                // checking for uniqueness handled separately; we can't throw exceptions from inside an Observer as
                // there will be no-one to catch it, resulting in the app crashing!
                validateMonikerUniqueTyping(editable.toString())
            }

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) { /* nada */ }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) { /* ditto */ }
        })

        // setup nameText textChangedListener to validate input
        nutshellText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                try{
                    validateNutshell(editable.toString())
                    hideError(nutshellError)
                } catch(e: InputMismatchException){
                    showError(nutshellError, if(e.message.isNullOrBlank()) "" else e.message!!)
                }
            }

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) { /* nada */ }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) { /* ditto */ }
        })
    }

    fun hideError(errorTextView: TextView){
        // Use View.GONE   instead of   View.INVISIBLE + height = 0
        // reason: once layout is inflated, setting the TextView's height correctly again becomes a nightmare
        errorTextView.visibility = View.GONE
    }

    fun showError(errorTextView: TextView, message: String){
        errorTextView.text = message
        errorTextView.visibility = View.VISIBLE
    }

    // validation necessary in View; ViewModel is not
    fun validateFullName(candidate: String){
        if(candidate.length > maxNameLength)
            throw InputMismatchException("Full name should be " + maxNameLength.toString() + " characters or less.")
    }

    fun validateMoniker(candidate: String) {
        var relationObserved = false

        // test length
        if(candidate.length > maxMonikerLength)
            throw InputMismatchException("Moniker should be " + maxMonikerLength.toString() + " characters or less.")

        // test format
        val regex = Regex("[a-zA-Z0-9]+")
        if(!regex.matches(candidate))
            throw InputMismatchException("Moniker should only contain numbers or letters.")
    }

    fun validateNutshell(candidate: String){
        if(candidate.length > maxNutshellLength)
            throw InputMismatchException("Description should be " + maxNutshellLength.toString() + " characters or less. ")
    }

    fun validateMonikerUniqueTyping(candidate: String){
        // test uniqueness
        viewModel.setRelationWithMoniker(candidate)
        val relWithMon = viewModel.relationWithMoniker
        relWithMon.observe(this, object: Observer<Relation>{
            override fun onChanged(t: Relation?) {
                // if relationId's match, we're talking about the same Relation - so the user has therefore input that
                // Relation's moniker as its currently now in the database
                if (t != null && t.relationId != currentRelationId)
                    showError(monikerError, "Moniker should be unique!")
                relWithMon.removeObserver(this)
            }
        })
    }

    fun validateMonikerUniqueSubmit(candidate: String){
        viewModel.setRelationWithMoniker(candidate)
        val relWithMon = viewModel.relationWithMoniker
        relWithMon.observe(this, object: Observer<Relation>{
            override fun onChanged(t: Relation?) {
                Log.i("@pj", "RelationWithMoniker = " + t?.getSuggestionName())
                // if relationId's match, we're talking about the same Relation - so the user has therefore input that
                // Relation's moniker as its currently now in the database
                if (t == null || t.relationId == currentRelationId) {
                    // this is the problem with LiveData... I need to set observables on it, and if there's a conditional
                    // action that itself depends on other LiveData I need to implement separate functions to implement
                    // the necessary observers
                    Log.i("@pj", "About to call updateRelationInDatabase()")
                    updateRelationInDatabase()
                }
                relWithMon.removeObserver(this)
            }
        })
    }

    fun updateRelationInDatabase(){
        // get the Relation object we're editing, set its attributes, and update the database
        viewModel.relationInFragment?.observe(this, Observer<Relation>{rel ->
            if(rel != null) {
                rel.fullName = nameText.editableText.toString()
                rel.currentMoniker = monikerText.editableText.toString()
                rel.nutshell = nutshellText.editableText.toString()
                Log.i("@pj","About to save changes to relation, with rel: " + rel.getSuggestionName())
                viewModel.saveChangesToRelation(rel)
            }
        })
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

}
