package pjvandamme.be.jocelyn.Presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
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
        // note that when the cursor's position is changed manually, no TextChangedEvent is triggered!
        val editTextField: EditText = findViewById(R.id.editJottingContent)
        editTextField.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val selStart: Int = editTextField.selectionStart
                val selEnd: Int = editTextField.selectionEnd
                var wordAtCursor: String? = getWordAtCursor(editable,selStart,selEnd).toString()

                /* testing */
                val characterAfter: TextView = findViewById(R.id.characterAfter)
                characterAfter.text = wordAtCursor

                wordAtCursor = getWordAtCursor(editable,0,0).toString()
                characterAfter.text = wordAtCursor

                if (wordAtCursor!!.isNotEmpty() && wordAtCursor?.get(0)=='@')
                    characterAfter.text = "Moniker detected!"
                /* end testing */
            }

            override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }
        })
    }

    // implement functionality of 'Back button' in the title bar, i.e. return to previous activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    /**
     * Gets the word found at the current cursor position.
     *
     * A "word" is a string of characters, delineated by whitespace (or the bounds of the editable).
     * A word can only be returned if the function is given a position (selStart == selEnd), not if it is given
     * a selection (selEnd > selStart).
     *
     * @param editable The editable containing text.
     * @param selStart Starting index of the cursor/selection in the editable.
     * @param selEnd Ending index of the cursor/selection in the editable.
     * @return The word found at the given position, or null if the given indices form a selection.
     */
    fun getWordAtCursor(editable: Editable?, selStart: Int, selEnd: Int): String?{
        // a word can only be gotten if the cursor is in a single position, not if it is a selection
        if (selStart == selEnd){
            // the word
            // desc and asc are counters; one moves to the left from the cursor, the other to the right
            /* desc starts at 1 position before the cursor's position, to prevent adding the character at this position
             * twice */
            var descendingIndex = selStart-1
            var ascendingIndex = selStart
            var searchedText = editable.toString()
            var currentChar = ' '
            var word = StringBuilder("")

            /* all characters to the left of the cursor are added to the stringbuilder, until whitespace is encountered
             * or we've reached the beginning of the editable */
            if(descendingIndex >=0)
                 currentChar = searchedText[descendingIndex]
            while(descendingIndex >= 0 && !currentChar.isWhitespace()){
                word.append(currentChar)
                // move to the left
                descendingIndex--
                // check whether we aren't going out of the editable's bounds
                if(descendingIndex >=0)
                    currentChar = searchedText[descendingIndex]
            }

            // characters before cursor were added in mirrored order, reverse puts them in the correct order
            word.reverse()

            // check if cursor is at the end of the editable
            if(ascendingIndex<searchedText.length) {
                /* all characters to the right of the cursor are added to the stringbuilder, until whitespace is encountered
                 * or we've reached the beginning of the editable */
                currentChar = searchedText[ascendingIndex]
                while (ascendingIndex < searchedText.length && !currentChar.isWhitespace()) {
                    word.append(currentChar)
                    // move to the right
                    ascendingIndex++
                    // check whether we aren't going out of the editable's bounds
                    if (ascendingIndex < searchedText.length)
                        currentChar = searchedText[ascendingIndex]
                }
            }

            return word.toString()
        }
        else
            return null
    }
}
