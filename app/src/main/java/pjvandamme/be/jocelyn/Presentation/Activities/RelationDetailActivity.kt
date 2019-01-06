package pjvandamme.be.jocelyn.Presentation.Activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_relation_detail.*
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.ViewModels.RelationDetailViewModel
import pjvandamme.be.jocelyn.Domain.ViewModels.RelationDetailViewModelFactory
import pjvandamme.be.jocelyn.R

class RelationDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: RelationDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relation_detail)
        setSupportActionBar(toolbar)

        // enabling a back button; navigationOnClickListener must be set explicitly for this collapsing toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        //initCollapsingToolbar()

        val extras: Bundle = intent.extras
        var relationMoniker = extras.getLong("relationId")

        viewModel = ViewModelProviders
            .of(this, RelationDetailViewModelFactory(application, relationMoniker))
            .get(RelationDetailViewModel::class.java)

        // set observer to LiveData change
        viewModel?.relation?.observe(this, object: Observer<Relation> {
            override fun onChanged(t: Relation?){
                val collapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.relation_detail_toolbar_layout)
                val nutshellTextView: TextView = findViewById(R.id.relationDetailNutshell)
                val monikerTextView: TextView = findViewById(R.id.relationDetailMoniker)
                val pictureImageView: ImageView = findViewById(R.id.relationPicture)
                pictureImageView.setImageResource(R.drawable._default)
                collapsingToolbar.title = t?.fullName
                nutshellTextView.text = t?.nutshell
                monikerTextView.text = "Current moniker: " + t?.currentMoniker
            }
        })

        // set the onclicklistener for the action button, taking the user to the relation overview
        overviewBtn.setOnClickListener {
            val relationsOverviewIntent = Intent(this, RelationsOverviewActivity::class.java)
            startActivity(relationsOverviewIntent)
        }
    }
}
