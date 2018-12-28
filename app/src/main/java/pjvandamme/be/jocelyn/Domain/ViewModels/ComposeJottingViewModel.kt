package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.AsyncTask
import android.text.SpannableString
import pjvandamme.be.jocelyn.Data.Persistence.JocelynDatabase
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.Models.Mention
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.JottingRepository
import pjvandamme.be.jocelyn.Domain.Repositories.MentionRepository
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

class ComposeJottingViewModel(application: Application) : AndroidViewModel(application) {
    private val jottingRepository: JottingRepository
    private val relationRepository: RelationRepository
    private val mentionRepository: MentionRepository
    val relations: LiveData<List<Relation>>
    val jottings: LiveData<List<Jotting>>

    init {
        jottingRepository = JottingRepository(application)
        relationRepository = RelationRepository(application)
        mentionRepository = MentionRepository(application)
        relations = relationRepository.getAllRelations()
        jottings = jottingRepository.getAllJottings()
    }

    fun addNewJotting(jotting: Jotting, mentions: List<Relation>?) {
        // call a specialized asynctask to handle the insertion of the new Jotting, and the creation and insertion of
        // the required Mention objects. This operation cannot be performed on the main thread to avoid locking the UI.
        AddNewJottingAsyncTask(jottingRepository, mentionRepository, mentions).execute(jotting)
    }

    /**
     * AddNewJottingAsyncTask is a subclass of AsyncTask that allows us to add a new Jotting to the database, get its
     * id, and use that id to create and insert the necessary Mentions. These operations should be handled on a
     * separate thread to avoid locking the UI.
     *
     * @param jottingRepository The JottingRepository responsible for handling database interactions regarding Jotting
     * objects.
     * @param mentionRepository The MentionRepository responsible for handling database interactions regarding Mention
     * objects, this reference will be passed to the AddNewMentionAsyncTask called during the execution of the task.
     * @param mentions A list of relations that are to be inserted as mentions for the new Jotting.
     */
    private class AddNewJottingAsyncTask(
        private val jottingRepository: JottingRepository,
        private val mentionRepository: MentionRepository,
        private val mentions: List<Relation>?
    ) : AsyncTask<Jotting, Void, Long>() {

        // insert the new Jotting and get its id as a return value
        override fun doInBackground(vararg jottings: Jotting): Long? {
            val newJottingId = jottingRepository.insert(jottings[0])
            return newJottingId
        }

        // once the Jotting has been inserted, and we've received its id, we can get on with creating and inserting
        // the required Mentions
        override fun onPostExecute(newJottingId: Long?) {
            super.onPostExecute(newJottingId)
            if (mentions != null && newJottingId != null) {
                for (mention in mentions!!) {
                    // call specialized asynctask for every Mention added, each creating a new thread to add a single
                    // Mention
                    AddNewMentionAsyncTask(mentionRepository).execute(Mention(0, newJottingId, mention.relationId))
                }
            }
        }

    }

    /**
     * AddNewJottingAsyncTask is a subclass of AsyncTask that allows us to add a new Mention to the database. This
     * operation should be handled on a separate thread to avoid locking the UI.
     *
     * @param mentionRepository The MentionRepository responsible for handling database interactions regarding Mention
     * objects.
     */
    private class AddNewMentionAsyncTask(
        private val mentionRepository: MentionRepository
    ) : AsyncTask<Mention, Void, Void>() {

        // insert the new Mention
        override fun doInBackground(vararg mentions: Mention): Void?{
            mentionRepository.insert(mentions[0])
            return null
        }
    }
}