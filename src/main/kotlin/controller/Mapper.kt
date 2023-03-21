package controller

interface Mapper<T, R> {
    fun T.toDTO(): R
    fun R.toDomain(): T
}
