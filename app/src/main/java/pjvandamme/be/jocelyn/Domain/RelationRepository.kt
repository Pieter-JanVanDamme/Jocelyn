package pjvandamme.be.jocelyn.Domain

import android.util.Log
import java.time.LocalDateTime
import java.util.*

class RelationRepository/*(private val relationDao: RelationDao)*/{

    val mentionSuggestionsLimit = 10
    var testdata: List<Relation> = listOf(
        Relation("1AB", "Jan Janssens", "JanJ", 5),
        Relation("1AC", "An Nemoon", "An", 15),
        Relation("1AD", "Anton ?", "AntonTirol", 20),
        Relation("1AE", "Ann Aerts", "Anneke", 37),
        Relation("1AF", "Andries De Bakker", "André", 28),
        Relation("1AE", "Antoon De Vleeschouwer", "Slagerke", 51),
        Relation("1AF", "Anne Van Clouseau", "AlsIkJouZie", 36)
    )

    // TODO: implement database-solution
    // TODO: lambda's
    fun getMentionSuggestions(start: String): List<Relation> {
        var matches: MutableList<Relation> = mutableListOf()
        var sorted: List<Relation>

        // find matches
        for(rel: Relation in testdata){
            if(rel.currentMoniker.startsWith(start, true) || rel.fullName.startsWith(start, true)) {
                // assure that full matches get to the top of the list
                if(rel.currentMoniker.toLowerCase() == start || rel.fullName.toLowerCase() == start)
                    matches.add(0, rel)
                else
                    matches.add(rel)
            }
        }

        // sort matches and limit to the top x mentionSuggestions
        sorted = matches.sortedWith(compareByDescending{it.mentions}).take(mentionSuggestionsLimit)

        return sorted
    }

    // testing data, to be replaced with call to DAO
    fun getAllMonikers():ArrayList<String>{
        var monikers: ArrayList<String> = arrayListOf(
            "Dave","Sofie","Jo","Leentje"
        )
        return monikers
    }

    fun assignNewMoniker(relation: Relation, newMoniker: String): String {
        val moniker = newMoniker.toLowerCase()
        // check if moniker is already in use, if not throw error
        validateMoniker(newMoniker)
        val oldMoniker = relation.currentMoniker
        // change the moniker
        return oldMoniker
    }

    // todo: testen
    fun validateMoniker(candidate: String) {
        val regex = Regex("[-_+*/!?@#$%&a-zA-Z0-9]+")
        if(regex.matches(candidate))
            return
        throw InputMismatchException("Moniker can only consist of (lowercase) letters, numbers, or any of " +
                "-_+*/!?@#$%&. No spaces are allowed.")
    }
}