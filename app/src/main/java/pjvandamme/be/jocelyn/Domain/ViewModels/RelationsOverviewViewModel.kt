package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

class RelationsOverviewViewModel(application: Application,
                                 private val relationRepository: RelationRepository
): AndroidViewModel(application){
    internal val allRelations: LiveData<List<Relation>>


    init {
        allRelations = relationRepository.getAll()
    }

}