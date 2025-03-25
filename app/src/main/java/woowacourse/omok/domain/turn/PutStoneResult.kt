package woowacourse.omok.domain.turn

import woowacourse.omok.domain.StoneState

sealed class PutStoneResult {
    data class NextTurn(
        val turn: StoneState,
    ) : PutStoneResult()

    data class Finished(
        val turn: StoneState,
    ) : PutStoneResult()

    data class Failure(
        val message: String,
    ) : PutStoneResult()
}
