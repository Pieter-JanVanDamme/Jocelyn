package pjvandamme.be.jocelyn.Domain.ViewModels

import android.arch.lifecycle.ViewModel
import android.app.Application
import android.arch.lifecycle.ViewModelProvider

/**
 * RelationSuggestionViewModelFactory allows RelationSuggestionFragment to set a search string as RelationSuggestion-
 * ViewModel's parameter, so that it's possible to filter relation suggestions based on the given search string.
 *
 * @param application Context of the activity or fragment using the factory.
 * @param searchString Searchstring to be set in the RelationSuggestionViewModel
 */
class RelationSuggestionViewModelFactory(private val application: Application, private val searchString: String) :
    ViewModelProvider.Factory {

    /**
     * Creates the RelationSuggestionVieModel, setting the given searchString for use by the ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RelationSuggestionViewModel(application, searchString) as T
    }
}