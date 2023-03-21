package controller

interface Mapper<S, D> {
    fun domainToDTO(source: S): D
    fun dtoToDomain(source: D): S
}
