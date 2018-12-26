package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

class RelationSuggestionViewModel(application: Application, val searchString: String): AndroidViewModel(application){
    private val relationRepository: RelationRepository = RelationRepository(application)
    internal val suggestedRelations: LiveData<List<Relation>>


    init {
        suggestedRelations = relationRepository.getRelationsFullNameOrCurrentMonikerLike("$searchString%")
    }

}