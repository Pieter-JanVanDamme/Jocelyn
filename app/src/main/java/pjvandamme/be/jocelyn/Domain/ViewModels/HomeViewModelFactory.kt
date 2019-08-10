package pjvandamme.be.jocelyn.Domain.ViewModels

import android.arch.lifecycle.ViewModel
import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.IJottingRepository
import pjvandamme.be.jocelyn.Domain.Repositories.JottingRepository

/**
 * Create EditRelationViewModelFactory allows us to set a Relation object that will be edited in the
 * EditRelationFragment.
 *
 * @param application Context of the activity or fragment using the factory.
 * @param recentJottingCount The amount of jottings (the most recent ones) the ViewModel will offer.
 */
class HomeViewModelFactory(private val application: Application,
                           private val recentJottingCount: Int,
                           private val jottingRepository: IJottingRepository<Jotting, Long>
) :
    ViewModelProvider.Factory {

    /**
     * Creates the CreateEditRelationViewModel, setting the given Relation object for use by the ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(application, recentJottingCount, jottingRepository) as T
    }
}