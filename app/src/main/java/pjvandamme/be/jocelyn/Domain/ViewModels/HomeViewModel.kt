package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.AsyncTask
import android.text.SpannableString
import pjvandamme.be.jocelyn.Data.Persistence.JocelynDatabase
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.Models.Mention
import pjvandamme.be.jocelyn.Domain.Models.Relation
import pjvandamme.be.jocelyn.Domain.Repositories.IJottingRepository
import pjvandamme.be.jocelyn.Domain.Repositories.JottingRepository
import pjvandamme.be.jocelyn.Domain.Repositories.MentionRepository
import pjvandamme.be.jocelyn.Domain.Repositories.RelationRepository

class HomeViewModel(application: Application,
                    recentJottingCount: Int,
                    private val jottingRepository: IJottingRepository<Jotting, Long>
) : AndroidViewModel(application) {
    val jottings: LiveData<List<Jotting>>

    init {
        jottings = jottingRepository.getLatestJottings(recentJottingCount)
    }


}