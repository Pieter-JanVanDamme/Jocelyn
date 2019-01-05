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
    fun insert(relation: Relation){
        relationDao.insert(relation)
    }

    @WorkerThread
    fun update(relation: Relation) {
        relationDao.update(relation)
    }

    @WorkerThread
    fun getByRelationId(id: Long): LiveData<Relation>{
        return relationDao.getByRelationId(id)
    }

    @WorkerThread
    fun getByCurrentMoniker(search: String): LiveData<Relation>{
        return relationDao.getByCurrentMoniker(search)
    }

    @WorkerThread
    fun getRelationsFullNameOrCurrentMonikerLike(search: String): LiveData<List<Relation>> {
        return relationDao.getRelationsFullNameOrCurrentMonikerLike(search)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: RelationDao):
        AsyncTask<Relation, Void, Void>() {
        override fun doInBackground(vararg params: Relation): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}