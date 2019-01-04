package pjvandamme.be.jocelyn.Presentation.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.ViewModels.CreateEditRelationViewModel
import pjvandamme.be.jocelyn.Domain.ViewModels.CreateEditRelationViewModelFactory
import pjvandamme.be.jocelyn.R

class CreateEditRelationFragment : Fragment() {

    var fragmentMode: CreateEditRelationFragmentMode? = null
    var receivedRelationAttributes: Boolean = false

    companion object {
        fun newInstance() = CreateEditRelationFragment()
    }

    private lateinit var viewModel: CreateEditRelationViewModel

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

        /* INITIALIZE VIEW ATTRIBUTES*/
        val titleText: TextView = view!!.findViewById(R.id.createUpdateFragmentTitle)
        val nameText: EditText = view!!.findViewById(R.id.editName)
        val monikerText: EditText = view!!.findViewById(R.id.editMoniker)
        val nutshellText: EditText = view!!.findViewById(R.id.editNutshell)

        // Setup in case EDIT MODE
        if(fragmentMode == CreateEditRelationFragmentMode.UPDATE) {
            // get the relationId, use it to change the state of our ViewModel so we may fetch the correct data
            var relationId: Long? = arguments?.getLong("relationId")
            if (relationId != null) {
                viewModel.reassignRelation(relationId)
                viewModel.relation?.observe(this, object: Observer<Relation>{
                    override fun onChanged(r: Relation?){
                        // prevent changes to EditText elements while the user is typing
                        if(!receivedRelationAttributes) {
                            nameText.setText(r?.fullName)
                            monikerText.setText(r?.currentMoniker)
                            nutshellText.setText(r?.nutshell)
                            receivedRelationAttributes = true
                        }
                    }
                })
            }
            titleText.setText(R.string.update_relation_fragment_title)
        }

        // Setup in case CREATE MODE
        if(fragmentMode == CreateEditRelationFragmentMode.CREATE){
            titleText.setText(R.string.create_relation_fragment_title)
            // we don't need to receive relation attributes
            receivedRelationAttributes = true
        }
    }

}
