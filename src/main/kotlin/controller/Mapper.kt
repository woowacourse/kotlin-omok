package controller

interface Mapper<T, R> {
    fun domainToDTO(domain: T): R
    fun dtoToDomain(dto: R): T
}
