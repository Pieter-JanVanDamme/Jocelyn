package pjvandamme.be.jocelyn.Domain.Models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.Date

/**
 * A Jotting represents a single typed note that contains a description or summary of one interaction the user had with
 * one or more Relations (i.e. other people).
 *
 * @property jottingId The auto-generated Id of the Jotting.
 * @property created The date the Jotting was created.
 * @property content the Actual content of the Jotting.
 */
@Entity(indices= [Index("jottingId")])
class Jotting(
        @PrimaryKey (autoGenerate = true) var jottingId: Long,
        @ColumnInfo(name="created") val created: Date,
        @ColumnInfo(name="content") var content: String
)