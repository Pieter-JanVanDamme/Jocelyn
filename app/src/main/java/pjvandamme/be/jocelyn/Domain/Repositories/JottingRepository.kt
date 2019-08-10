package pjvandamme.be.jocelyn.Domain.Repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.support.annotation.WorkerThread
import pjvandamme.be.jocelyn.Data.Persistence.JocelynDatabase
import pjvandamme.be.jocelyn.Data.Persistence.JottingDao
import pjvandamme.be.jocelyn.Domain.Models.Jotting

class JottingRepository(application: Application, private val jottingDao: JottingDao): IJottingRepository<Jotting, Long> {
    //private val jottingDao: JottingDao
    private val jottings: LiveData<List<Jotting>>

    init {
        //val jocelynRoomDatabase = JocelynDatabase.getJocelynDatabase(application)
        //jottingDao = jocelynRoomDatabase?.jottingDao()!!
        jottings = jottingDao?.getAll()
    }

    // GET
    override fun getById(id: Long): LiveData<Jotting>{
        return jottingDao.getById(id)
    }

    override fun getAll(): LiveData<List<Jotting>>{
        return jottings
    }

    // INSERT
    @WorkerThread
    override fun insert(jotting: Jotting) {
        jottingDao.insert(jotting)
    }
    override fun insertAndGenerateId(jotting: Jotting): Long{
        // only local database implemented at the moment; TODO: implement fetching data from network
        return jottingDao.insert(jotting)
    }

    // UPDATE
    @WorkerThread
    override fun update(jotting: Jotting) {
        jottingDao.update(jotting)
    }

    // DELETE
    @WorkerThread
    override fun delete(jotting: Jotting) {
        jottingDao.delete(jotting)
    }

    @WorkerThread
    override fun deleteAll(){
        jottingDao.deleteAll()
    }

    // SPECIFIC
    @WorkerThread
    override fun getJottingsByMention(relationId: Long): LiveData<List<Jotting>>{
        return jottingDao.getByMention(relationId)
    }

    @WorkerThread
    override fun getLatestJottings(count: Int): LiveData<List<Jotting>>{
        return jottingDao.getLatestJottings(count)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: JottingDao):
        AsyncTask<Jotting, Void, Void>() {
        override fun doInBackground(vararg params: Jotting): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}