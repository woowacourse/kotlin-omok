package omok.domain.turn

import omok.domain.StoneState

sealed class PutStoneResult {
    data class Success<T>(val result: T) : PutStoneResult() {
        class NextTurn(val turn: StoneState)
        class Finished(val turn: StoneState)
    }

    data class Failure(val message: String) : PutStoneResult()
}
