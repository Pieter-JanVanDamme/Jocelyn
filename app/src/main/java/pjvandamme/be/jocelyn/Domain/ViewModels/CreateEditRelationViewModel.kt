package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

class CreateEditRelationViewModel(application: Application, initRelation: Relation?) : AndroidViewModel(application) {
    val relationRepository: RelationRepository
    var relation: LiveData<Relation>? = null

    init {
        relationRepository = RelationRepository(application)
        if(initRelation != null)
            relation = relationRepository.getByRelationId(initRelation.relationId)
    }

    fun reassignRelation(id: Long){
        relation = relationRepository.getByRelationId(id)
    }
}
