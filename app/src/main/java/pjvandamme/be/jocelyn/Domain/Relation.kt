package pjvandamme.be.jocelyn.Domain

import java.util.*

/**
 *  The Relation class
 *
 *  @param fullName Relation's full name
 *  @param currentMoniker Moniker currently used to refer to this Relation in Jottings. Must be unique,
 *  @param mentions The amount of times this Relation was mentioned in Jottings, helps determine closeness.
 *  todo: complete docs
 */
class Relation(
        val id: String,
        // TODO: add perhaps a nutshell = brief description
        var fullName: String,
        currentMoniker: String,
        mentions: Long,
        // TODO: implement custom pictures
        var picture: Int = -1
        // TODO: implement priority
        //var priority: Int = 4
){
    var currentMoniker = currentMoniker
        private set(value) { /* cannot directly set new Moniker, see RelationRepository */}

    var mentions  = mentions
        private set(value) { /* impossible to directly set value */}

    fun updateMentions(){
        mentions++
    }

    fun getSuggestionName(): String = "$fullName ($currentMoniker)"
}