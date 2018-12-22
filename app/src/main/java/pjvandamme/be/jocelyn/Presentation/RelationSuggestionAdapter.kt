package pjvandamme.be.jocelyn.Presentation

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import pjvandamme.be.jocelyn.Domain.Relation
import pjvandamme.be.jocelyn.R


class RelationSuggestionAdapter(context: Context, content: ArrayList<Relation>):
        ArrayAdapter<Relation>(context, android.R.layout.simple_list_item_1, content) {
    var relations: ArrayList<Relation> = content
    var tempRelations: ArrayList<Relation> = content
    var suggestions: ArrayList<Relation> = content

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var relation: Relation? = getItem(position)
        var view: View
        if(convertView == null)
            view = LayoutInflater.from(context).inflate(R.layout.relation_suggestion_row, parent)
        else
            view = convertView

        // create the Text- and Imageview
        var tvRelation: TextView = view.findViewById(R.id.relationSugg)
        var ivRelation: ImageView = view.findViewById(R.id.relationSuggImg)

        // set the TextView text and the ImageView image
        if(tvRelation != null)
            tvRelation.text = relation?.getSuggestionName()

        if(ivRelation != null && relation != null && relation?.picture != -1)
            ivRelation.setImageResource(relation.picture)
        else
            ivRelation.setImageResource(R.drawable.placeholder)

        // assign alternate colors for rows
        if(position%2 == 0)
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRelSuggEvenBackground))
        else
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRelSuggOddBackground))

        return view
    }

    override fun getFilter():Filter {
        return RelationSuggestionFilter()
    }

    inner class RelationSuggestionFilter: Filter(){
        override fun convertResultToString(resultValue: Any): CharSequence {
            val relation = resultValue as Relation
            return relation.getSuggestionName()
        }

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            if (constraint != null) {
                this@RelationSuggestionAdapter.suggestions.clear()
                for (relations in this@RelationSuggestionAdapter.tempRelations) {
                    if (relations.fullName.toLowerCase().startsWith(constraint.toString().toLowerCase())
                        || relations.currentMoniker.startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(relations)
                    }
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                return filterResults
            } else {
                return Filter.FilterResults()
            }
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            var r: ArrayList<Relation> = arrayListOf()
            if(results != null)
                r = results.values as ArrayList<Relation>
            if (results != null && results.count > 0) {
                clear()
                for (rel: Relation in r) {
                    add(rel)
                    notifyDataSetChanged()
                }
            } else {
                clear()
                notifyDataSetChanged()
            }
        }
    }
}