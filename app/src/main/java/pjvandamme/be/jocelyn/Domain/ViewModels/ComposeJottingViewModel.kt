package pjvandamme.be.jocelyn.Domain.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.text.SpannableString
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.Repositories.JottingRepository

class ComposeJottingViewModel(application: Application): AndroidViewModel(application){
    private val jottingRepository: JottingRepository
    //internal val allJottings: LiveData<List<Jotting>>

    var jottingContent: SpannableString = SpannableString("")

    init {
        jottingRepository = JottingRepository(application)
        //allJottings = jottingRepository.getAllJottings()
    }


}