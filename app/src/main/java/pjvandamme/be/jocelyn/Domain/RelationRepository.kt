package pjvandamme.be.jocelyn.Domain

import java.time.LocalDateTime

class RelationRepository/*(private val relationDao: RelationDao)*/{
    // testing data, to be replaced with LiveData from DAO
    val words: List<Relation> = arrayListOf(
        Relation("David Janssens","Dave", 9999),
        Relation("Sofie Peeters","Sofie", 7777),
        Relation("Johan (man van Sofie)","Jo", 3333),
        Relation("Leen Temmerman","Leentje", 1234)
    )

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

        //returns old moniker
        return ""
    }
}