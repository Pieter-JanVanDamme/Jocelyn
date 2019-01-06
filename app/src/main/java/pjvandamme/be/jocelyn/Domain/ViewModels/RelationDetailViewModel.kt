package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.JottingRepository
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

class RelationDetailViewModel(application: Application, relationId: Long) : AndroidViewModel(application) {
    val relationRepository: RelationRepository
    val jottingRepository: JottingRepository
    var relation: LiveData<Relation>
    var jottings: LiveData<List<Jotting>>


    init{
        relationRepository = RelationRepository(application)
        jottingRepository = JottingRepository(application)
        relation = relationRepository.getByRelationId(relationId)
        jottings = jottingRepository.getJottingsByMention(relationId)
    }
}