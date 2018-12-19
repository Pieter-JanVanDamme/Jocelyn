package pjvandamme.be.jocelyn.Presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import pjvandamme.be.jocelyn.R

class ComposeJottingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_jotting)
        setSupportActionBar(findViewById(R.id.compose_jotting_toolbar))

        // change the title of the title bar
        supportActionBar?.title = "Compose Jotting"

        // implement a 'Back button' in the title bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // implement functionality of 'Back button' in the title bar, i.e. return to previous activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
