package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.IJottingRepository
import pjvandamme.be.jocelyn.Domain.Repositories.IRelationRepository
import pjvandamme.be.jocelyn.Domain.Repositories.JottingRepository
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

class RelationDetailViewModel(application: Application,
                              relationId: Long,
                              private val relationRepository: IRelationRepository<Relation, Long>,
                              private val jottingRepository: IJottingRepository<Jotting, Long>
) : AndroidViewModel(application) {
    //val jottingRepository: JottingRepository
    var relation: LiveData<Relation>
    var jottings: LiveData<List<Jotting>>


    init{
        //jottingRepository = JottingRepository(application)
        relation = relationRepository.getById(relationId)
        jottings = jottingRepository.getJottingsByMention(relationId)
    }
}