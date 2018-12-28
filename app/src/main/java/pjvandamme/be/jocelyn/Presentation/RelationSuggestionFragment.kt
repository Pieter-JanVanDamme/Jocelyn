package pjvandamme.be.jocelyn.Presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository
import pjvandamme.be.jocelyn.Domain.ViewModels.RelationSuggestionViewModel
import pjvandamme.be.jocelyn.Domain.ViewModels.RelationSuggestionViewModelFactory
import pjvandamme.be.jocelyn.R


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [RelationSuggestionFragment.OnListFragmentInteractionListener] interface.
 */
class RelationSuggestionFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 3

    private var relationSuggestionViewModel: RelationSuggestionViewModel? = null
    private var listener: OnListFragmentInteractionListener? = null
    private var viewAdapter: MyRelationSuggestionRecyclerViewAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var searchString: String? = arguments?.getString("searchString")
        if(searchString.isNullOrBlank())
            searchString = ""

        // uses RelationSuggestionViewModelFactory to be able to set the searchstring
        // in the RelationSuggestionViewModel
        relationSuggestionViewModel = ViewModelProviders
            .of(this, RelationSuggestionViewModelFactory(activity?.application!!, searchString))
            .get(RelationSuggestionViewModel::class.java)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_relationsuggestion_list, container, false)
        viewAdapter = MyRelationSuggestionRecyclerViewAdapter(listener)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = viewAdapter
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // set the suggestedRelations observer
        relationSuggestionViewModel?.suggestedRelations?.observe(this, object: Observer<List<Relation>> {
            override fun onChanged(t: List<Relation>?) {
                //relationSuggestionViewModel?.updateSearchString(arguments?.getString("searchString"))
                viewAdapter?.updateRelationSuggestions(t)
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(relation: Relation?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            RelationSuggestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
