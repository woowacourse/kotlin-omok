package woowacourse.omok.domain.board.result

import woowacourse.omok.domain.board.Point

sealed class Finished : PlaceStoneResult {
    data class GameFinished(
        val point: Point,
    ) : Finished()

    data class BoardFull(
        val point: Point,
    ) : Finished()
}
