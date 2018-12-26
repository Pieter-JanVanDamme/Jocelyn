package pjvandamme.be.jocelyn.Domain.Models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.ForeignKey

@Entity(foreignKeys = [
        ForeignKey(entity= Jotting::class, parentColumns = ["jottingId"], childColumns=["jottingId"]),
        ForeignKey(entity= Relation::class, parentColumns = ["relationId"], childColumns=["relationId"])
])
class Mention(
        @PrimaryKey(autoGenerate = true) var id: Long,
        @ColumnInfo(name = "jottingId") val jottingId: Long,
        @ColumnInfo(name = "relationId") val relationId: Long,
        @ColumnInfo(name = "order") val order: Int
)