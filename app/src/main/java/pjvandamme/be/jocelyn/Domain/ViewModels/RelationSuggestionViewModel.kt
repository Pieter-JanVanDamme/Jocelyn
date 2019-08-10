package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.IRelationRepository
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

class RelationSuggestionViewModel(application: Application,
                                  val searchString: String,
                                  private val relationRepository: IRelationRepository<Relation, Long>
): AndroidViewModel(application){
    internal val suggestedRelations: LiveData<List<Relation>>


    init {
        suggestedRelations = relationRepository.getWhereFullNameOrCurrentMonikerLike("$searchString%")
    }

}