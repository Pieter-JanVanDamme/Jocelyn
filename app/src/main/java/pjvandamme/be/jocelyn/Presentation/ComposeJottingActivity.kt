package pjvandamme.be.jocelyn.Presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_compose_jotting.*
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

        // textChangedListener implemented as anonymous inner class
        val editTextField: EditText = findViewById(R.id.editJottingContent)
        editTextField.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val afterEditable: TextView = findViewById(R.id.afterEditable)
                afterEditable.text = editable.toString()
            }

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
                val beforeSequence: TextView = findViewById(R.id.beforeSeq)
                beforeSequence.text = sequence.toString()
                val beforeStart: TextView = findViewById(R.id.beforeStart)
                beforeStart.text = start.toString()
                val beforeCount: TextView = findViewById(R.id.beforeCount)
                beforeCount.text = count.toString()
                val beforeAfter: TextView = findViewById(R.id.beforeAfter)
                beforeAfter.text = after.toString()
            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                val onSequence: TextView = findViewById(R.id.onSeq)
                onSequence.text = sequence.toString()
                val onStart: TextView = findViewById(R.id.onStart)
                onStart.text = start.toString()
                val onBefore: TextView = findViewById(R.id.onBefore)
                onBefore.text = before.toString()
                val onCount: TextView = findViewById(R.id.onCount)
                onCount.text = count.toString()
            }
        })
    }

    // implement functionality of 'Back button' in the title bar, i.e. return to previous activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}
