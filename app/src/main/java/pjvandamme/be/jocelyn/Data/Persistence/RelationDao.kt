package pjvandamme.be.jocelyn.Data.Persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query
import pjvandamme.be.jocelyn.Domain.Models.Relation

@Dao
interface RelationDao {
    @Insert
    fun insert(relation: Relation)

    @Query("SELECT * FROM relation ORDER BY relationId ASC")
    fun getAllRelations(): LiveData<List<Relation>>

    @Query("SELECT currentMoniker FROM relation ORDER BY currentMoniker ASC")
    fun getAllMonikers(): LiveData<List<String>>

    @Query("SELECT * FROM relation WHERE currentMoniker = :moniker")
    fun getRelationMonikerEquals(moniker: String): LiveData<Relation>

    @Query("SELECT * FROM relation WHERE fullName = :name")
    fun getRelationFullnameEquals(name: String): LiveData<Relation>

    @Query("SELECT * FROM relation WHERE fullName LIKE :search")
    fun getRelationsFullNameLike(search: String): LiveData<List<Relation>>

    @Query("SELECT * FROM relation WHERE currentMoniker LIKE :search")
    fun getRelationsCurrentMonikerLike(search: String): LiveData<List<Relation>>

    @Query("SELECT * FROM relation WHERE (fullName LIKE :search) OR (currentMoniker LIKE :search)")
    fun getRelationsFullNameOrCurrentMonikerLike(search: String): LiveData<List<Relation>>

    @Query("DELETE FROM relation")
    fun deleteAll()
}