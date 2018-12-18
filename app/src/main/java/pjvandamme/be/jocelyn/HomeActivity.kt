package pjvandamme.be.jocelyn

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import pjvandamme.be.jocelyn.R.id.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val actionbtn: FloatingActionButton = findViewById(addJottingBtn)
        actionbtn.setOnClickListener{
            val composeJottingIntent = Intent(this, ComposeJottingActivity::class.java)
            startActivity(composeJottingIntent)
        }

    }
}
