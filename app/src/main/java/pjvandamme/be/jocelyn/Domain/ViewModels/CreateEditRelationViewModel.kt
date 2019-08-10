package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.IRelationRepository
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

class CreateEditRelationViewModel(application: Application,
                                  initRelation: Relation?,
                                  private val relationRepository: IRelationRepository<Relation, Long>
                                  ) : AndroidViewModel(application) {
    var relationInFragment: LiveData<Relation>? = null
    var relationWithMoniker: LiveData<Relation>

    init {
        if(initRelation != null)
            relationInFragment = relationRepository.getById(initRelation.relationId)
        relationWithMoniker = relationRepository.getByCurrentMoniker("")
    }

    fun setRelationInFragment(id: Long){
        relationInFragment = relationRepository.getById(id)
    }

    fun setRelationWithMoniker(moniker: String){
        relationWithMoniker = relationRepository.getByCurrentMoniker(moniker.toLowerCase())
    }
    
    fun saveChangesToRelation(relation: Relation){
        UpdateRelationAsyncTask(relationRepository).execute(relation)
    }

    fun insertRelation(relation: Relation){
        InsertRelationAsyncTask(relationRepository).execute(relation)
    }

    private class UpdateRelationAsyncTask(
        private val relationRepository: IRelationRepository<Relation, Long>
    ) : AsyncTask<Relation, Void, Void>() {

        // insertAndGenerateId the new Mention
        override fun doInBackground(vararg relations: Relation): Void?{
            relationRepository.update(relations[0])
            return null
        }
    }

    private class InsertRelationAsyncTask(
        private val relationRepository: IRelationRepository<Relation, Long>
    ) : AsyncTask<Relation, Void, Void>() {

        // insertAndGenerateId the new Mention
        override fun doInBackground(vararg relations: Relation): Void?{
            relationRepository.insert(relations[0])
            return null
        }
    }
}
