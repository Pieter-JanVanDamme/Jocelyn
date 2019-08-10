package pjvandamme.be.jocelyn.Data.Persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import pjvandamme.be.jocelyn.Domain.Models.Relation

@Dao
interface RelationDao {
    //GET
    @Query("SELECT * FROM relation WHERE relationId = :id")
    fun getById(id: Long): LiveData<Relation>

    @Query("SELECT * FROM relation ORDER BY mentions DESC")
    fun getAll(): LiveData<List<Relation>>

    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(relation: Relation)

    // UPDATE
    @Update
    fun update(relation: Relation)

    // DELETE
    @Delete
    fun delete(relation: Relation)

    @Query("DELETE FROM relation")
    fun deleteAll()

    // MONIKERS
    @Query("SELECT currentMoniker FROM relation ORDER BY currentMoniker ASC")
    fun getAllMonikers(): LiveData<List<String>>

    @Query("SELECT * FROM relation WHERE currentMoniker = :moniker")
    fun getByCurrentMoniker(moniker: String): LiveData<Relation>

    @Query("SELECT * FROM relation WHERE (fullName LIKE :search) OR (currentMoniker LIKE :search) LIMIT 10")
    fun getWhereFullNameOrCurrentMonikerLike(search: String): LiveData<List<Relation>>
}