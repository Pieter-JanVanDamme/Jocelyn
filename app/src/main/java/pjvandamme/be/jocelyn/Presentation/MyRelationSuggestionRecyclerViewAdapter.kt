package pjvandamme.be.jocelyn.Presentation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.relationsuggestion_list_content.view.*
import pjvandamme.be.jocelyn.R
import pjvandamme.be.jocelyn.Presentation.RelationSuggestionFragment.OnListFragmentInteractionListener
import pjvandamme.be.jocelyn.Domain.Relation

/**
 * [RecyclerView.Adapter] that can display a [Relation] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyRelationSuggestionRecyclerViewAdapter(
    private val relationSuggestions: List<Relation>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyRelationSuggestionRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val rel = v.tag as Relation
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(rel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.relationsuggestion_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val relation = relationSuggestions[position]
        // TODO: implement custom pictures
        holder.relPicture.setImageResource(R.drawable._default)
        holder.relName.text = relation.getSuggestionName()

        with(holder.view) {
            tag = relation
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = relationSuggestions.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val relPicture: ImageView = view.suggestionPicture
        val relName: TextView = view.suggestionName
    }
}
