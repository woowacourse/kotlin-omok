package woowacourse.omok.model

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.Stone
import woowacourse.omok.model.entity.StoneColor
import woowacourse.omok.model.turn.BlackTurn
import woowacourse.omok.model.turn.Finished
import woowacourse.omok.model.turn.Turn
import woowacourse.omok.model.turn.WhiteTurn

class OmokGame private constructor(var turn: Turn) {
    fun run(
        inputPoint: () -> Pair<Int, Int>,
        beforeTurn: (String?, Pair<Int, Int>?) -> Unit,
        afterGame: (String?) -> Unit,
        onInappropriate: (String) -> Unit,
    ) {
        while (turn !is Finished) {
            beforeTurn(turn.board.lastStoneColor()?.name, turn.board.previousStone()?.let { it.point.x to it.point.y })
            proceedTurn(inputPoint, onInappropriate)
        }
        afterGame(turn.board.lastStoneColor()?.name)
    }

    private fun proceedTurn(
        inputPoint: () -> Pair<Int, Int>,
        onInappropriate: (String) -> Unit,
    ) {
        val point = inputPoint()
        turn = turn.placeStone(Point(point.first, point.second), onInappropriate)
    }

    companion object {
        fun of(stoneColor: StoneColor?, stones: Set<Stone>): OmokGame {
            return when (stoneColor) {
                StoneColor.BLACK -> OmokGame(WhiteTurn(Board(stones)))
                StoneColor.WHITE -> OmokGame(BlackTurn(Board(stones)))
                else -> OmokGame(BlackTurn(Board()))
            }
        }
    }
}
