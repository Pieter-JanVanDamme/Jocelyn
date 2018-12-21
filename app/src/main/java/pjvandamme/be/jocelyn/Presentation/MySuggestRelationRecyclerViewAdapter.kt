package pjvandamme.be.jocelyn.Presentation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pjvandamme.be.jocelyn.R


import pjvandamme.be.jocelyn.Presentation.SuggestRelationFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_suggestrelation.view.*
import pjvandamme.be.jocelyn.Domain.Relation


class MySuggestRelationRecyclerViewAdapter(
    private val suggestedRelations: List<Relation>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MySuggestRelationRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Relation
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    // Inflate the layout, i.e. create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_suggestrelation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = suggestedRelations[position]
        holder.mIdView.text = item.id
        holder.mContentView.text = item.getSuggestionName()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = suggestedRelations.size

    // ViewHolder, binds data as needed from model into widgets for row in our list
    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
