package pjvandamme.be.jocelyn.Presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.widget.ImageView
import android.widget.TextView
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.ViewModels.ComposeJottingViewModel
import pjvandamme.be.jocelyn.R
import pjvandamme.be.jocelyn.R.id.*
import android.view.View
import android.widget.Button
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.ViewModels.RelationSuggestionViewModel
import pjvandamme.be.jocelyn.Domain.ViewModels.RelationSuggestionViewModelFactory


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        val logoView: ImageView = findViewById(R.id.logoView)
        logoView.bringToFront()

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
