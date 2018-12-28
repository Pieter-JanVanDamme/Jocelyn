package pjvandamme.be.jocelyn.Data.Persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import java.util.*

@Dao
interface JottingDao{
    @Insert
    fun insert(jotting: Jotting): Long

    @Query("SELECT * FROM jotting ORDER BY jottingId DESC")
    fun getAllJottings(): LiveData<List<Jotting>>

    @Query("SELECT * FROM jotting WHERE created > :date")
    fun loadAllJottingsMoreRecentThan(date: Date): LiveData<List<Jotting>>

    @Query("DELETE FROM jotting")
    fun deleteAll()
}