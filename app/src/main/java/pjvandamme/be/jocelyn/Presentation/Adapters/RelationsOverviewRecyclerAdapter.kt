package pjvandamme.be.jocelyn.Presentation.Adapters

import android.app.Activity
import android.widget.Toast
//import sun.security.krb5.internal.KDCOptions.with
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.PopupMenu
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Presentation.Fragments.CreateEditRelationFragment
import pjvandamme.be.jocelyn.Presentation.Fragments.RelationSuggestionFragment
import pjvandamme.be.jocelyn.R
import android.support.v4.app.FragmentActivity
import android.util.Log
import pjvandamme.be.jocelyn.Presentation.Fragments.CreateEditRelationFragmentMode


class RelationsOverviewRecyclerAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RelationsOverviewRecyclerAdapter.ViewHolder>() {

    private var relationsList: List<Relation> = listOf()

    // create and configure the ViewHolder for a particular card
    // has a Viewgroup that will hold the views managed by the holder...
    // ... and an Integer representing the view type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.relation_card, parent, false)
        return ViewHolder(itemView)
    }

    // updates the ViewHolder based on model data for a certain position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val relation = relationsList!![position]
        holder.count.text = relation.mentions.toString() + " mentions"
        holder.title.text = relation.fullName
        holder.thumbnail.setImageResource(R.drawable._default)

        holder.overflow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                showPopupMenu(holder.overflow, relation)
            }
        })
    }

    // show popup menu when clicking on the overflow = 3 dots
    private fun showPopupMenu(view: View, relation: Relation) {
        // inflate menu
        val popup = PopupMenu(mContext, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_relation, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListener(mContext, relation))
        popup.show()
    }

    // setting the list of relations and notifying the change in data set, so that the view can be recreated
    // if we don't do this, getItemCount() will always return 0 and the view will not be created
    fun updateRelationSuggestions(newRelations: List<Relation>?){
        if(newRelations != null) {
            this.relationsList = newRelations
            notifyDataSetChanged()
        }
    }

    // click listener for popup menu
    internal inner class MyMenuItemClickListener(private val mContext: Context, private val relation: Relation)
            : PopupMenu.OnMenuItemClickListener {

        var fragMan: FragmentManager? = null

        init {
            try {
                fragMan = (mContext as FragmentActivity).supportFragmentManager
            } catch (e: ClassCastException) {
                Log.e("ClassCastException: ", "Can't get fragment manager")
            }

        }

        // put the relation's id in a bundle, for use by either the view-profile-action OR the edit-information-
        // action. This allows the activity or fragment called to retrieve the Relation from the DB based on their
        // unique id
        fun getRelationMonikerAsBundle(): Bundle {
            var fragmentArguments = Bundle()
            fragmentArguments.putLong("relationId", relation.relationId)
            return fragmentArguments
        }

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.action_view_profile -> {
                    Toast.makeText(mContext, R.string.toast_view_profile, Toast.LENGTH_SHORT).show()
                    // todo
                    return true
                }
                R.id.action_edit_information -> {
                    var editFragment = CreateEditRelationFragment()
                    editFragment.arguments = getRelationMonikerAsBundle()

                    if(fragMan != null){
                        // show the popup informing the user what's about to happen
                        Toast.makeText(mContext, R.string.toast_edit_information, Toast.LENGTH_SHORT).show()

                        // next, we'll add the fragment to RelationsOverviewActivity
                        var fragTrans = fragMan?.beginTransaction()

                        // remove the previously added fragment, if any
                        fragTrans?.remove(editFragment)

                        // create new fragment, set Bundle containing relation id and mode as arguments
                        // we set the CreateEditRelationFragmentMode as an argument as Fragments can't have non-default
                        // constructors
                        editFragment = CreateEditRelationFragment()
                        var fragArgs: Bundle = getRelationMonikerAsBundle()
                        fragArgs.putString("mode", CreateEditRelationFragmentMode.UPDATE.mode)
                        editFragment.arguments = fragArgs

                        // add fragment to RelationsOverviewActivity
                        fragTrans?.add(R.id.editRelationContainer, editFragment)
                        fragTrans?.commit()
                    } else {
                        Toast.makeText(mContext, R.string.menu_error, Toast.LENGTH_SHORT).show()
                    }
                    return true
                }
            }
            return false
        }
    }


    // a class to describe each item view (relation_card) and its place within the RecyclerView
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var count: TextView
        var thumbnail: ImageView
        var overflow: ImageView

        init {
            title = view.findViewById(R.id.title)
            count = view.findViewById(R.id.count)
            thumbnail = view.findViewById(R.id.thumbnail) as ImageView
            overflow = view.findViewById(R.id.overflow) as ImageView
        }
    }

    override fun getItemCount(): Int {
        return relationsList.size
    }
}