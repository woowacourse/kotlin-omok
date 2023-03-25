package woowacourse.omok.database.repository

interface Repository<T> {
    fun getAll(): List<T>
    fun insert(item: T)
    fun isEmpty(): Boolean
    fun clear()
    fun close()
}
