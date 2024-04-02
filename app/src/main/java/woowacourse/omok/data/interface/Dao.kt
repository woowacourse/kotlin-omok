package woowacourse.omok.data.`interface`

interface Dao<T> {
    fun save(entity: T): T

    fun saveAll(entities: List<T>) {
        entities.forEach { save(it) }
    }

    fun findAll(): List<T>

    fun findLastOrNull(): T?

    fun deleteAll()
}