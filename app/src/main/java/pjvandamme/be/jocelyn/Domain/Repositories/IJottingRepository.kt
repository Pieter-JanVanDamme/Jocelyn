package pjvandamme.be.jocelyn.Domain.Repositories

import android.arch.lifecycle.LiveData

interface IJottingRepository<Jotting, Long>: Repository<Jotting, Long> {
    // SPECIFIC
    fun insertAndGenerateId(jotting: Jotting): Long
    fun getJottingsByMention(relationId: Long): LiveData<List<Jotting>>
    fun getLatestJottings(count: Int): LiveData<List<Jotting>>
}