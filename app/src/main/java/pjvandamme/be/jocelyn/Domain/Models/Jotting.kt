package pjvandamme.be.jocelyn.Domain.Models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(indices= [Index("jottingId")])
class Jotting(
        @PrimaryKey (autoGenerate = true) var jottingId: Long,
        @ColumnInfo(name="created") val created: Date,
        @ColumnInfo(name="content") var content: String
)