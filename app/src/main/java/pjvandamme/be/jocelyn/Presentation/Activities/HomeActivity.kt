package pjvandamme.be.jocelyn.Presentation.Activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import pjvandamme.be.jocelyn.R
import pjvandamme.be.jocelyn.R.id.*
import android.widget.Button
import pjvandamme.be.jocelyn.Data.Persistence.JocelynDatabase
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.Repositories.JottingRepository
import pjvandamme.be.jocelyn.Domain.ViewModels.HomeViewModel
import pjvandamme.be.jocelyn.Domain.ViewModels.HomeViewModelFactory
import pjvandamme.be.jocelyn.Presentation.Adapters.JottingsRecyclerAdapter


class HomeActivity : AppCompatActivity() {

    private val recentJottingCount = 10

    private lateinit var viewModel: HomeViewModel
    private var adapter: JottingsRecyclerAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var jottingsList: List<Jotting>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val actionbtn: FloatingActionButton = findViewById(addJottingBtn)
        actionbtn.setOnClickListener{
            val composeJottingIntent = Intent(this, ComposeJottingActivity::class.java)
            startActivity(composeJottingIntent)
        }


        /***************** TESTING PURPOSES ***************************************************************************/
        /**************************************************************************************************************/
        val relationbtn: Button = findViewById(relationsBtn)
        relationbtn.setOnClickListener{
            val relationsOverviewIntent = Intent(this, RelationsOverviewActivity::class.java)
            startActivity(relationsOverviewIntent)
        }
        /**************************************************************************************************************/
        /**************************************************************************************************************/

        viewModel = ViewModelProviders
            .of(this, HomeViewModelFactory(
                application,
                recentJottingCount,
                JottingRepository(this.application, JocelynDatabase.getJocelynDatabase(this.application)!!.jottingDao())
            ))
            .get(HomeViewModel::class.java)

        recyclerView = findViewById(R.id.home_latest_jottings_recycler)

        jottingsList = mutableListOf()
        adapter = JottingsRecyclerAdapter(this)

        val mLayoutManager = GridLayoutManager(this, 1)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter

        viewModel?.jottings?.observe(this, object: Observer<List<Jotting>> {
            override fun onChanged(t: List<Jotting>?){
                jottingsList = t
                adapter?.updateJottings(jottingsList)
            }
        })
    }
}
