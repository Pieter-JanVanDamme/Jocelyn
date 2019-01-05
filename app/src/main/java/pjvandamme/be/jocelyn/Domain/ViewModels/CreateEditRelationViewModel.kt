package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.util.Log
import pjvandamme.be.jocelyn.Data.Persistence.RelationDao
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository
import java.util.*

class CreateEditRelationViewModel(application: Application, initRelation: Relation?) : AndroidViewModel(application) {
    val relationRepository: RelationRepository
    var relationInFragment: LiveData<Relation>? = null
    var relationWithMoniker: LiveData<Relation>

    init {
        relationRepository = RelationRepository(application)
        if(initRelation != null)
            relationInFragment = relationRepository.getByRelationId(initRelation.relationId)
        relationWithMoniker = relationRepository.getByCurrentMoniker("")
    }

    fun setRelationInFragment(id: Long){
        relationInFragment = relationRepository.getByRelationId(id)
    }

    fun setRelationWithMoniker(moniker: String){
        Log.i("@pj", "Setting relation with Moniker to " + moniker)
        relationWithMoniker = relationRepository.getByCurrentMoniker(moniker)
    }
    
    fun saveChangesToRelation(relation: Relation){
        UpdateRelationAsyncTask(relationRepository).execute(relation)
    }

    private class UpdateRelationAsyncTask(
        private val relationRepository: RelationRepository
    ) : AsyncTask<Relation, Void, Void>() {

        // insert the new Mention
        override fun doInBackground(vararg relations: Relation): Void?{
            Log.i("@pj", "About to update using asynctask")
            relationRepository.update(relations[0])
            return null
        }
    }
}
