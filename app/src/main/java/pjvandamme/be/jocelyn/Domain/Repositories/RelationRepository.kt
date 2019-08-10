package pjvandamme.be.jocelyn.Domain.Repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.support.annotation.WorkerThread
import pjvandamme.be.jocelyn.Data.Persistence.JocelynDatabase
import pjvandamme.be.jocelyn.Data.Persistence.RelationDao
import pjvandamme.be.jocelyn.Domain.Models.Relation

class RelationRepository(application: Application, private val relationDao: RelationDao) : IRelationRepository<Relation, Long> {
    //private val relationDao: RelationDao
    val relations: LiveData<List<Relation>>

    init {
        //val jocelynRoomDatabase = JocelynDatabase.getJocelynDatabase(application)
        //relationDao = jocelynRoomDatabase?.relationDao()!!
        relations = relationDao?.getAll()
    }

    // GET
    @WorkerThread
    override fun getById(id: Long): LiveData<Relation>{
        return relationDao.getById(id)
    }

    @WorkerThread
    override fun getAll(): LiveData<List<Relation>>{
        return relationDao.getAll()
    }

    // INSERT
    @WorkerThread
    override fun insert(relation: Relation){
        relationDao.insert(relation)
    }


    // UPDATE
    @WorkerThread
    override fun update(relation: Relation) {
        relationDao.update(relation)
    }

    // DELETE
    @WorkerThread
    override fun delete(relation: Relation) {
        relationDao.delete(relation)
    }

    @WorkerThread
    override fun deleteAll(){
        relationDao.deleteAll()
    }


    //MONIKER
    @WorkerThread
    override fun getByCurrentMoniker(search: String): LiveData<Relation>{
        return relationDao.getByCurrentMoniker(search)
    }

    @WorkerThread
    override fun getWhereFullNameOrCurrentMonikerLike(search: String): LiveData<List<Relation>> {
        return relationDao.getWhereFullNameOrCurrentMonikerLike(search)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: RelationDao):
        AsyncTask<Relation, Void, Void>() {
        override fun doInBackground(vararg params: Relation): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}