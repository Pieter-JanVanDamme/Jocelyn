package pjvandamme.be.jocelyn.Domain.Repositories

import android.arch.lifecycle.LiveData

interface IRelationRepository<Relation, Long>: Repository<Relation, Long> {
    // MONIKER
    fun getByCurrentMoniker(search: String): LiveData<Relation>
    fun getWhereFullNameOrCurrentMonikerLike(search: String): LiveData<List<Relation>>
}