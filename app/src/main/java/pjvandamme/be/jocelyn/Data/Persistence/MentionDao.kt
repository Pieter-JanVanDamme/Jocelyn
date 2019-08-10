package pjvandamme.be.jocelyn.Data.Persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import pjvandamme.be.jocelyn.Domain.Models.Mention

@Dao
interface MentionDao {
    // GET
    @Query("SELECT * FROM mention WHERE id = :id")
    fun getById(id: Long): LiveData<Mention>

    @Query("SELECT * FROM mention ORDER BY jottingId DESC")
    fun getAll(): LiveData<List<Mention>>

    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mention: Mention)

    // UPDATE
    @Update
    fun update(mention: Mention)

    // DELETE
    @Delete
    fun delete(mention: Mention)

    @Query("DELETE FROM mention")
    fun deleteAll()
}