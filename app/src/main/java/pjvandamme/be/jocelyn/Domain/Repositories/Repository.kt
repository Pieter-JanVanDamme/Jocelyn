package pjvandamme.be.jocelyn.Domain.Repositories

interface Repository<T, K> {
    fun getById(id: K): T
    fun getAll(): List<T>
    fun add(entity: T)
    fun addRange(entities: List<T>)
    fun remove(entity: T)
    fun removeRange(entities: List<T>)
}