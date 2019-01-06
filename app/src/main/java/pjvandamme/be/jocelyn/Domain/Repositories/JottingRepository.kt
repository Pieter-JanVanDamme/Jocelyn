package pjvandamme.be.jocelyn.Domain.Repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.support.annotation.WorkerThread
import pjvandamme.be.jocelyn.Data.Persistence.JocelynDatabase
import pjvandamme.be.jocelyn.Data.Persistence.JottingDao
import pjvandamme.be.jocelyn.Domain.Models.Jotting

class JottingRepository(application: Application){
    private val jottingDao: JottingDao
    private val jottings: LiveData<List<Jotting>>

    init {
        val jocelynRoomDatabase = JocelynDatabase.getJocelynDatabase(application)
        jottingDao = jocelynRoomDatabase?.jottingDao()!!
        jottings = jottingDao?.getAllJottings()
    }

    fun getAllJottings(): LiveData<List<Jotting>>{
        return jottings
    }

    @WorkerThread
    fun insert(jotting: Jotting): Long{
        // only local database implemented at the moment; TODO: implement fetching data from network
        return jottingDao.insert(jotting)
    }

    @WorkerThread
    fun getJottingsByMention(relationId: Long): LiveData<List<Jotting>>{
        return jottingDao.getJottingsByMention(relationId)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: JottingDao):
        AsyncTask<Jotting, Void, Void>() {
        override fun doInBackground(vararg params: Jotting): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}