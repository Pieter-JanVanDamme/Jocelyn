package pjvandamme.be.jocelyn.Presentation

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.TextView
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.ViewModels.ComposeJottingViewModel
import pjvandamme.be.jocelyn.R
import pjvandamme.be.jocelyn.R.id.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        /* TESTING THE INSERTION */
        val composeJottingViewModel = ComposeJottingViewModel(this.application)

        var jottingsObserver = Observer<List<Jotting>> { jottingsList ->
            val test: TextView = findViewById(testView)
            var all: String = ""
            if(jottingsList != null) {
                for (jot in jottingsList) {
                    all += jot.jottingId.toString() + " " + jot.created + " " + jot.content + "\n\n"
                }
            }
            test.text = all
        }

        composeJottingViewModel?.jottings?.observe(this, jottingsObserver)

        val actionbtn: FloatingActionButton = findViewById(addJottingBtn)
        actionbtn.setOnClickListener{
            val composeJottingIntent = Intent(this, ComposeJottingActivity::class.java)
            startActivity(composeJottingIntent)
        }

    }
}
