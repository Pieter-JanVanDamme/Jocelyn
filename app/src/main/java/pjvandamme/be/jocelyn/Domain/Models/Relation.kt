package pjvandamme.be.jocelyn.Domain.Models

import java.util.*
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 *  The Relation class
 *
 *  @param fullName Relation's full name
 *  @param currentMoniker Moniker currently used to refer to this Relation in Jottings. Must be unique,
 *  @param mentions The amount of times this Relation was mentioned in Jottings, helps determine closeness.
 *  todo: complete docs
 */
@Entity(indices = [Index("currentMoniker"), Index("relationId"), Index("fullName")])
class Relation(
        @PrimaryKey(autoGenerate = true) var relationId: Long,
        @ColumnInfo(name = "fullName")var fullName: String,
        @ColumnInfo(name = "currentMoniker") var currentMoniker: String,
        @ColumnInfo(name = "nutshell") var nutshell: String,
        @ColumnInfo(name = "mentions") var mentions: Long,
        // TODO: implement custom pictures
        var picture: String = "_default"
){
    fun getSuggestionName(): String = "$fullName ($currentMoniker)"
}