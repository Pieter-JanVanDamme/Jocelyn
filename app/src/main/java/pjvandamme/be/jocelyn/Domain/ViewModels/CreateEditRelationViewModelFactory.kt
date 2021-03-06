package pjvandamme.be.jocelyn.Domain.ViewModels

import android.arch.lifecycle.ViewModel
import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.IRelationRepository
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

/**
 * Create EditRelationViewModelFactory allows us to set a Relation object that will be edited in the
 * EditRelationFragment.
 *
 * @param application Context of the activity or fragment using the factory.
 * @param relation The Relation object to be edited.
 */
class CreateEditRelationViewModelFactory(private val application: Application,
                                         private val relation: Relation?,
                                         private val relationRepository: IRelationRepository<Relation, Long>
) :
    ViewModelProvider.Factory {

    /**
     * Creates the CreateEditRelationViewModel, setting the given Relation object for use by the ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateEditRelationViewModel(application, relation, relationRepository) as T
    }
}