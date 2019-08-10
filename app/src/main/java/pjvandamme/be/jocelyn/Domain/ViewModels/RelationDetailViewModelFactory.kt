package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.IJottingRepository
import pjvandamme.be.jocelyn.Domain.Repositories.IRelationRepository
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

/**
 * RelationSuggestionViewModelFactory allows RelationSuggestionFragment to set a search string as RelationSuggestion-
 * ViewModel's parameter, so that it's possible to filter relation suggestions based on the given search string.
 *
 * @param application Context of the activity or fragment using the factory.
 * @param relationId The relationId that the ViewModel can use to get the required Relation LiveData.
 */
class RelationDetailViewModelFactory(private val application: Application,
                                     private val relationId: Long,
                                     private val relationRepository: IRelationRepository<Relation, Long>,
                                     private val jottingRepository: IJottingRepository<Jotting, Long>
) :
    ViewModelProvider.Factory {

    /**
     * Creates the RelationDetailViewModel, giving it the relationId for initialization of the ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RelationDetailViewModel(application, relationId, relationRepository, jottingRepository) as T
    }
}