package pjvandamme.be.jocelyn.Domain.Repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.support.annotation.WorkerThread
import pjvandamme.be.jocelyn.Data.Persistence.JocelynDatabase
import pjvandamme.be.jocelyn.Data.Persistence.MentionDao
import pjvandamme.be.jocelyn.Domain.Models.Mention

class MentionRepository(application: Application, private val mentionDao: MentionDao): IMentionRepository<Mention, Long> {
    //private val mentionDao: MentionDao
    private val mentions: LiveData<List<Mention>>

    init {
        //val jocelynRoomDatabase = JocelynDatabase.getJocelynDatabase(application)
        //mentionDao = jocelynRoomDatabase?.mentionDao()!!
        mentions = mentionDao?.getAll()
    }

    // GET
    @WorkerThread
    override fun getById(id: Long): LiveData<Mention>{
        return mentionDao.getById(id)
    }

    @WorkerThread
    override fun getAll(): LiveData<List<Mention>>{
        // only local database implemented at the moment; TODO: implement fetching data from network
        return mentions
    }

    // INSERT

    @WorkerThread
    override fun insert(mention: Mention){
        // only local database implemented at the moment; TODO: implement fetching data from network
        mentionDao.insert(mention)
    }

    // UPDATE
    @WorkerThread
    override fun update(mention: Mention) {
        mentionDao.update(mention)
    }

    // DELETE
    @WorkerThread
    override fun delete(mention: Mention) {
        mentionDao.delete(mention)
    }

    @WorkerThread
    override fun deleteAll(){
        mentionDao.deleteAll()
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: MentionDao):
            AsyncTask<Mention, Void, Void>() {
        override fun doInBackground(vararg params: Mention): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }
}