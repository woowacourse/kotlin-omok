package omok.domain.turn

sealed class PutStoneResult {
    data class Success(val turn: Turn) : PutStoneResult()
    data class Failure(val message: String) : PutStoneResult()
}