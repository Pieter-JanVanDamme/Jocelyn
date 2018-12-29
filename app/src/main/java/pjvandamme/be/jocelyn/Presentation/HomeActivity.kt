package pjvandamme.be.jocelyn.Presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.widget.ImageView
import android.widget.TextView
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.ViewModels.ComposeJottingViewModel
import pjvandamme.be.jocelyn.R
import pjvandamme.be.jocelyn.R.id.*
import android.view.View
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.ViewModels.RelationSuggestionViewModel
import pjvandamme.be.jocelyn.Domain.ViewModels.RelationSuggestionViewModelFactory


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val logoView: ImageView = findViewById(R.id.logoView)
        logoView.bringToFront()

        /* TESTING THE INSERTION **************************************************************************************/
        val relationSuggestionViewModel = ViewModelProviders
            .of(this, RelationSuggestionViewModelFactory(this.application, ""))
            .get(RelationSuggestionViewModel::class.java)

        var relationsObserver = Observer<List<Relation>> { relationList ->
            val test: TextView = findViewById(testView)
            var all: String = ""
            if(relationList != null) {
                for (rel in relationList) {
                    all += rel.getSuggestionName() + " mentions: " + rel.mentions
                }
            }
            test.text = all
        }

        relationSuggestionViewModel?.suggestedRelations?.observe(this, relationsObserver)

        /* ************************************************************************************************************/

        val actionbtn: FloatingActionButton = findViewById(addJottingBtn)
        actionbtn.setOnClickListener{
            val composeJottingIntent = Intent(this, ComposeJottingActivity::class.java)
            startActivity(composeJottingIntent)
        }

    }

    override fun onStart(){
        super.onStart()
        val logoView: ImageView = findViewById(R.id.logoView)
        // Handler allows us to send/process runnables with the thread's MessageQueue
        Handler().postDelayed({
            logoView.visibility = View.INVISIBLE
        }, 3000)
    }
}
