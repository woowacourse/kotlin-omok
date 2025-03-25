package woowacourse.omok.domain

sealed class PutStoneResult {
    data class NextTurn(
        val turn: StoneState,
    ) : PutStoneResult()

    data class Finished(
        val turn: StoneState,
    ) : PutStoneResult()

    data object InvalidPosition : PutStoneResult()

    data object AlreadyPlaced : PutStoneResult()

    data object Violation : PutStoneResult()
}
