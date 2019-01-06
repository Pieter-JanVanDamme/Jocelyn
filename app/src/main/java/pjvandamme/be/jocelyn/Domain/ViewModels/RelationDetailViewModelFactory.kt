package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * RelationSuggestionViewModelFactory allows RelationSuggestionFragment to set a search string as RelationSuggestion-
 * ViewModel's parameter, so that it's possible to filter relation suggestions based on the given search string.
 *
 * @param application Context of the activity or fragment using the factory.
 * @param relationId The relationId that the ViewModel can use to get the required Relation LiveData.
 */
class RelationDetailViewModelFactory(private val application: Application, private val relationId: Long) :
    ViewModelProvider.Factory {

    /**
     * Creates the RelationDetailViewModel, giving it the relationId for initialization of the ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RelationDetailViewModel(application, relationId) as T
    }
}