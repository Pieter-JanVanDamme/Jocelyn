package pjvandamme.be.jocelyn.Data.Persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query
import pjvandamme.be.jocelyn.Domain.Models.Mention

@Dao
interface MentionDao {
    @Insert
    fun insert(mention: Mention)

    @Query("SELECT * FROM mention ORDER BY jottingId DESC")
    fun getAllMentions(): LiveData<List<Mention>>

    @Query("DELETE FROM mention")
    fun deleteAll()
}