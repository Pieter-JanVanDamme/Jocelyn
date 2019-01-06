package pjvandamme.be.jocelyn.Presentation.Adapters

import android.content.Context
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.*
import pjvandamme.be.jocelyn.R
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import java.text.SimpleDateFormat


class JottingsRecyclerAdapter(private val mContext: Context) :
    RecyclerView.Adapter<JottingsRecyclerAdapter.ViewHolder>() {

    private var jottingsList: List<Jotting> = listOf()

    // create and configure the ViewHolder for a particular note
    // has a Viewgroup that will hold the views managed by the holder...
    // ... and an Integer representing the view type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.jotting_note, parent, false)
        return ViewHolder(itemView)
    }

    // updates the ViewHolder based on model data for a certain position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jotting = jottingsList!![position]
        holder.date.text = SimpleDateFormat("dd/MM/yyyy HH:mm").format(jotting.created)
        holder.content.text = jotting.content
    }

    // setting the list of jottings and notifying the change in data set, so that the view can be recreated
    // if we don't do this, getItemCount() will always return 0 and the view will not be created
    fun updateJottings(jottings: List<Jotting>?){
        if(jottings != null) {
            this.jottingsList = jottings
            notifyDataSetChanged()
        }
    }


    // a class to describe each item view (jotting_note) and its place within the RecyclerView
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var date: TextView
        var content: TextView

        init {
            date = view.findViewById(R.id.jottingNoteDate)
            content = view.findViewById(R.id.jottingNoteContent)
        }
    }

    override fun getItemCount(): Int {
        return jottingsList.size
    }
}