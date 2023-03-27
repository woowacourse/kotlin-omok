package repository

interface Repository<T> {
    suspend fun getAll(): List<T>
    fun insert(item: T)
    fun isEmpty(): Boolean
    fun clear()
    fun close()
}
