package pjvandamme.be.jocelyn.Presentation

import android.widget.Toast
//import sun.security.krb5.internal.KDCOptions.with
import android.content.Context
import android.support.v7.widget.PopupMenu
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.R


class RelationsOverviewRecyclerAdapter(private val mContext: Context, private val relationList: List<Relation>) :
    RecyclerView.Adapter<RelationsOverviewRecyclerAdapter.ViewHolder>() {

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
        val relation = relationList[position]
        holder.title.text = relation.fullName
        holder.count.text = relation.mentions.toString() + " mentions"
        holder.thumbnail.setImageResource(R.drawable._default)

        holder.overflow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                showPopupMenu(holder.overflow)
            }
        })
    }

    // show popup menu when clicking on the overflow = 3 dots
    private fun showPopupMenu(view: View) {
        // inflate menu
        val popup = PopupMenu(mContext, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_relation, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListener())
        popup.show()
    }

    // click listener for popup menu
    internal inner class MyMenuItemClickListener : PopupMenu.OnMenuItemClickListener {

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.getItemId()) {
                R.id.action_view_profile -> {
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show()
                    return true
                }
                R.id.action_edit_information -> {
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show()
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
        return relationList.size
    }
}