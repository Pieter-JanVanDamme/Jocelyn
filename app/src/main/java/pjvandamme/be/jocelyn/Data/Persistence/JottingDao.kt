package pjvandamme.be.jocelyn.Data.Persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import java.util.*

@Dao
interface JottingDao{
    //GET
    @Query("SELECT * FROM jotting WHERE jottingId = :id")
    fun getById(id: Long): LiveData<Jotting>

    @Query("SELECT * FROM jotting ORDER BY jottingId DESC")
    fun getAll(): LiveData<List<Jotting>>

    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(jotting: Jotting): Long

    // UPDATE
    @Update
    fun update(jotting: Jotting)

    // DELETE
    @Delete
    fun delete(jotting: Jotting)

    @Query("DELETE FROM jotting")
    fun deleteAll()

    // SPECIFIC

    @Query("SELECT * FROM jotting AS j JOIN mention AS m ON j.jottingId = m.jottingId WHERE m.relationId = :relId ORDER BY j.created DESC")
    fun getByMention(relId: Long): LiveData<List<Jotting>>

    @Query("SELECT * FROM jotting ORDER BY created DESC LIMIT :count")
    fun getLatestJottings(count: Int): LiveData<List<Jotting>>

    @Query("SELECT * FROM jotting WHERE created > :date")
    fun loadAllJottingsMoreRecentThan(date: Date): LiveData<List<Jotting>>
}