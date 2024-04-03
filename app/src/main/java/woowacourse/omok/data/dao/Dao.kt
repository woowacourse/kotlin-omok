package woowacourse.omok.data.dao

interface Dao<T> {
    fun save(entity: T): T

    fun saveAll(entities: List<T>): List<T> {
        return entities.map { save(it) }
    }

    fun findAll(): List<T>

    fun findLastOrNull(): T?

    fun deleteAll()
}
