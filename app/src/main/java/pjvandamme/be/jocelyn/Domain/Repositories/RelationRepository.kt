package pjvandamme.be.jocelyn.Domain.Repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.support.annotation.WorkerThread
import pjvandamme.be.jocelyn.Data.Persistence.JocelynDatabase
import pjvandamme.be.jocelyn.Data.Persistence.RelationDao
import pjvandamme.be.jocelyn.Domain.Models.Relation
import java.util.*

class RelationRepository(application: Application){
    private val relationDao: RelationDao
    val relations: LiveData<List<Relation>>

    init {
        val jocelynRoomDatabase = JocelynDatabase.getJocelynDatabase(application)
        relationDao = jocelynRoomDatabase?.relationDao()!!
        relations = relationDao?.getAllRelations()
    }

    @WorkerThread
    fun getAllRelations(): LiveData<List<Relation>>{
        return relationDao.getAllRelations()
    }

    @WorkerThread
    fun getByCurrentMoniker(moniker: String): LiveData<Relation>{
        return relationDao.getRelationByMoniker(moniker)
    }

    @WorkerThread
    fun getRelationsFullNameLike(search: String): LiveData<List<Relation>> {
        // only local database implemented at the moment; TODO: implement fetching data from network
        return relationDao.getRelationsFullNameLike(search.toLowerCase())
    }

    @WorkerThread
    fun getRelationsCurrentMonikerLike(search: String): LiveData<List<Relation>> {
        // only local database implemented at the moment; TODO: implement fetching data from network
        return relationDao.getRelationsCurrentMonikerLike(search.toLowerCase())
    }

    @WorkerThread
    fun getRelationsFullNameOrCurrentMonikerLike(search: String): LiveData<List<Relation>> {
        return relationDao.getRelationsFullNameOrCurrentMonikerLike(search)
    }

    @WorkerThread
    fun getAllMonikers(): LiveData<List<String>> {
        // only local database implemented at the moment; TODO: implement fetching data from network
        return relationDao.getAllMonikers()
    }

    fun assignNewMoniker(relation: Relation, newMoniker: String): String {
        val moniker = newMoniker.toLowerCase()
        // check if moniker is already in use, if not throw error
        validateMoniker(newMoniker)
        val oldMoniker = relation.currentMoniker
        // change the moniker
        return oldMoniker
    }

    // todo: testen
    fun validateMoniker(candidate: String) {
        val regex = Regex("[-_+*/!?@#$%&a-zA-Z0-9]+")
        if(regex.matches(candidate))
            return
        throw InputMismatchException("Moniker can only consist of (lowercase) letters, numbers, or any of " +
                "-_+*/!?@#$%&. No spaces are allowed.")
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: RelationDao):
        AsyncTask<Relation, Void, Void>() {
        override fun doInBackground(vararg params: Relation): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}