package pjvandamme.be.jocelyn.Domain.Models

import android.arch.persistence.room.*

@Entity(foreignKeys = [
        ForeignKey(entity= Jotting::class, parentColumns = ["jottingId"], childColumns=["jottingId"]),
        ForeignKey(entity= Relation::class, parentColumns = ["relationId"], childColumns=["relationId"])],
indices = [Index("jottingId"), Index("relationId"), Index("id")])
class Mention(
        @PrimaryKey(autoGenerate = true) var id: Long,
        @ColumnInfo(name = "jottingId") val jottingId: Long,
        @ColumnInfo(name = "relationId") val relationId: Long
)