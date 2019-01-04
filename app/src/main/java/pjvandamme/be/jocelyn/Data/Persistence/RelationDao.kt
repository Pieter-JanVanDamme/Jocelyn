package pjvandamme.be.jocelyn.Data.Persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import pjvandamme.be.jocelyn.Domain.Models.Relation

@Dao
interface RelationDao {
    @Insert
    fun insert(relation: Relation)

    @Update
    fun update(relation: Relation)

    @Query("SELECT * FROM relation ORDER BY mentions DESC")
    fun getAllRelations(): LiveData<List<Relation>>

    @Query("SELECT currentMoniker FROM relation ORDER BY currentMoniker ASC")
    fun getAllMonikers(): LiveData<List<String>>

    @Query("SELECT * FROM relation WHERE relationId = :id")
    fun getByRelationId(id: Long): LiveData<Relation>

    @Query("SELECT * FROM relation WHERE (fullName LIKE :search) OR (currentMoniker LIKE :search) LIMIT 10")
    fun getRelationsFullNameOrCurrentMonikerLike(search: String): LiveData<List<Relation>>

    @Query("DELETE FROM relation")
    fun deleteAll()
}