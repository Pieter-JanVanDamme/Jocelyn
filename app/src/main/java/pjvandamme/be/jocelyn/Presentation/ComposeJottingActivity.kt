package pjvandamme.be.jocelyn.Presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.TextAppearanceSpan
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import pjvandamme.be.jocelyn.Domain.Relation
import pjvandamme.be.jocelyn.Domain.RelationRepository
import pjvandamme.be.jocelyn.R

class ComposeJottingActivity : AppCompatActivity() {

    val mentionChar = '@'
    val relationRepository: RelationRepository = RelationRepository()

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
                // get the cursor's position
                val selStart: Int = editTextField.selectionStart
                val selEnd: Int = editTextField.selectionEnd

                // find word at the current cursor position
                var wordAtCursor: String? = getWordAtCursor(editable,selStart,selEnd).toString()
                var relationTextView: TextView = findViewById(R.id.relationSuggestions)

                // determine if word at the current cursor position is a Mention
                if (wordAtCursor!!.isNotEmpty() && wordAtCursor?.get(0)==mentionChar) {
                    /* APPLY STYLING */
                    editTextField.removeTextChangedListener(this) // prevent infinite loop
                    var newContents = applyMentionsStyling(editable, getMentionsIndices(editable, mentionChar))
                    editTextField.setText(newContents)
                    // return cursor to original position
                    editTextField.setSelection(selStart)
                    editTextField.addTextChangedListener(this)

                    /* SUGGESTIONS LIST */
                    var searchString: String = wordAtCursor.removePrefix("@")
                    relationTextView.text = relationRepository.getMentionSuggestions(searchString).toString()
                }
                else{
                    relationTextView.text = ""
                }
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
     * @param editable The editable containing the text.
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

    /**
     * Finds the indices of (both inclusive) all words (separated by spaces) beginning with the given initChar
     * in the given editable.
     *
     * For example: if the editable contains the text "Hello @Jocelyn and @Matt" and has the initChar '@', then
     * the result will be [[6,13],[19,23]].
     *
     * It's important to note that a mention 1) is bordered by spaces (or the editable's boundaries), 2) begins with
     * the initChar. An editable with the text "Hello @Jocelyn@Matt" and an initChar '@' given to this function will
     * return [[6,18]] whereas the text "Hello @Jocelyn @Matt" with the same initChar would return [[6,13],[15,19]].
     *
     * @param editable The editable containing the text to be searched.
     * @param initChar The character that marks the beginning of a mention.
     * @return A 2-dimensional list containing the beginning and end positions of each mention.
     */
    fun getMentionsIndices(editable: Editable?, initChar: Char): List<List<Int>>{
        val searchText = editable.toString()
        var mentions: MutableList<List<Int>> = mutableListOf()
        var count = 0
        var position = 0
        var endposition: Int

        while(position < searchText.length) {
            position = searchText.indexOf(initChar, position, true)
            // break if no (further) mention was found
            if (position == -1)
                break
            // continue if found character is not preceded by a space
            if (position-1 >= 0 && searchText[position-1] != ' ') {
                position++ // update position so next mention can be found, otherwise an endless loop occurs
                continue
            }

            endposition = searchText.indexOf(' ', position, true)
            if (endposition == -1) // mention is at the end of editable
                endposition = searchText.length

            mentions.add(count, listOf(position, endposition-1))

            count++ // update count
            position++ // update position so next mention can be found, otherwise an endless loop occurs
        }

        return mentions
    }

    /**
     * Returns all monikers used (i.e. 'mentioned') in a given editable. A moniker is mentioned by directly preceding it
     * with a specific initial character (initChar, usually '@'). A 'mention' is the use of the initChar together with
     * a moniker.
     *
     * Suppose that the editable contains the text "Hello @Jocelyn @Bob@Gary!", and the initChar were '@', then this
     * function would return ["Jocelyn","Bob@Gary"].
     *
     * @param editable The editable containing the text to be searched.
     * @param initChar The character that marks the beginning of a mention.
     * @return A list containing the monikers of the mentions in a given editable text.
     */
    fun getMentionsText(editable: Editable?, initChar: Char): List<String>{
        val searchText = editable.toString()
        var mentions: MutableList<String> = mutableListOf()
        var mentionText: String
        val indices = getMentionsIndices(editable, initChar)
        for(i in indices.indices){
            mentionText = searchText.substring(indices[i][0]+1,indices[i][1]+1)
            if(!mentionText.isEmpty())
                mentions.add(i, mentionText)
        }
        return mentions
    }

    /**
     * Applies the style designated for Mentions by transforming the editable into a SpannableString and
     * applying TextAppearanceSpans to Mentions.
     *
     * @param editable The editable containing the text to be styled/formatted.
     * @param mentionsIndices Indices of the Mentions that are to receive the styling/formatting.
     * @return A SpannableString containing the styled/formatted text.
     */
    fun applyMentionsStyling(editable: Editable?, mentionsIndices: List<List<Int>>): SpannableString {
        var compositionSpan = SpannableString(editable)

        for(i in mentionsIndices.indices){
            compositionSpan.setSpan(TextAppearanceSpan(
                this,
                R.style.MentionStyle),
                mentionsIndices[i][0],
                mentionsIndices[i][1]+1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return compositionSpan
    }
}
