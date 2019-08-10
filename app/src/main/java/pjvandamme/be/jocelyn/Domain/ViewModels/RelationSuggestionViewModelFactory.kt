package pjvandamme.be.jocelyn.Domain.ViewModels

import android.arch.lifecycle.ViewModel
import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.IRelationRepository
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

/**
 * RelationSuggestionViewModelFactory allows RelationSuggestionFragment to set a search string as RelationSuggestion-
 * ViewModel's parameter, so that it's possible to filter relation suggestions based on the given search string.
 *
 * @param application Context of the activity or fragment using the factory.
 * @param searchString Searchstring to be set in the RelationSuggestionViewModel
 */
class RelationSuggestionViewModelFactory(private val application: Application,
                                         private val searchString: String,
                                         private val relationRepository: IRelationRepository<Relation, Long>) :
    ViewModelProvider.Factory {

    /**
     * Creates the RelationSuggestionVieModel, setting the given searchString for use by the ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RelationSuggestionViewModel(application, searchString, relationRepository) as T
    }
}