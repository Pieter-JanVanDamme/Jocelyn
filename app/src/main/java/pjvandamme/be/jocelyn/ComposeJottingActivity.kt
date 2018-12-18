package pjvandamme.be.jocelyn

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
class AddJottingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_jotting)
        setSupportActionBar(findViewById(R.id.compose_jotting_toolbar))
        supportActionBar?.title = "Compose Jotting"
    }
}
