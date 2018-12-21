package pjvandamme.be.jocelyn.Domain

import java.time.LocalDateTime
import java.util.*

class RelationRepository/*(private val relationDao: RelationDao)*/{
    // todo: testing data, to be replaced with LiveData from DAO
    val relations: List<Relation> = arrayListOf(
        Relation("1AB", "Jan Janssens", "JanJ",5),
        Relation("1AC", "An Nemoon", "An",15),
        Relation("1AD", "Anton ?", "AntonTirol", 20),
        Relation("1AE", "Ann Aerts", "Anneke", 37),
        Relation("1AF", "Andries De Bakker", "André", 28),
        Relation("1AE", "Antoon De Vleeschouwer", "Slagerke", 51),
        Relation("1AF", "Anne Van Clouseau", "AlsIkJouZie", 36)
    )

    // Todo: remove this and implement better methods
    companion object tester {
        fun getAllRelations(): List<Relation> = arrayListOf(
            Relation("1AB", "Jan Janssens", "JanJ", 5),
            Relation("1AC", "An Nemoon", "An", 15),
            Relation("1AD", "Anton ?", "AntonTirol", 20),
            Relation("1AE", "Ann Aerts", "Anneke", 37),
            Relation("1AF", "Andries De Bakker", "André", 28),
            Relation("1AE", "Antoon De Vleeschouwer", "Slagerke", 51),
            Relation("1AF", "Anne Van Clouseau", "AlsIkJouZie", 36)
        )
    }

    // testing data, to be replaced with call to DAO
    fun getAllMonikers():List<String>{
        var monikers: List<String> = arrayListOf(
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

    fun validateMoniker(candidate: String) {
        val regex = Regex("[-_+*/!?@#$%&a-zA-Z0-9]+")
        if(regex.matches(candidate))
            return
        throw InputMismatchException("Moniker can only consist of (lowercase) letters, numbers, or any of " +
                "-_+*/!?@#$%&. No spaces are allowed.")
    }
}