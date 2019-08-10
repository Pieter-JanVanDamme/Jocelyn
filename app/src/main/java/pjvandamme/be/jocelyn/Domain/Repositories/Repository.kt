package pjvandamme.be.jocelyn.Domain.Repositories

import android.arch.lifecycle.LiveData

interface Repository<T, K> {
    // GET
    fun getById(id: K): LiveData<T>
    fun getAll(): LiveData<List<T>>

    // INSERT
    fun insert(entity: T)
    //fun insertRange(entities: List<T>)

    // UPDATE
    fun update(entity: T)
    //fun updateRange(entities: List<T>)

    // DELETE
    fun delete(entity: T)
    //fun deleteRange(entities: List<T>)
    fun deleteAll()
}