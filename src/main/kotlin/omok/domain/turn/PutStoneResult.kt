package omok.domain.turn

sealed class PutStoneResult {
    data class Success<T>(val result: T) : PutStoneResult()

    data class Failure(val message: String) : PutStoneResult()
}
