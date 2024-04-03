package woowacourse.omok.model

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.Stone
import woowacourse.omok.model.entity.StoneColor
import woowacourse.omok.model.turn.BlackTurn
import woowacourse.omok.model.turn.Finished
import woowacourse.omok.model.turn.Turn
import woowacourse.omok.model.turn.WhiteTurn

class OmokGame private constructor(private var currentTurn: Turn) {
    private var previousTurn = currentTurn

    val placeStoneColor
        get() = currentTurn.board.lastStoneColor()

    fun run(
        inputPoint: () -> Pair<Int, Int>,
        beforeTurn: (String?, Pair<Int, Int>?) -> Unit,
        afterGame: (String?) -> Unit,
        onInappropriate: (String) -> Unit,
    ) {
        while (currentTurn !is Finished) {
            beforeTurn(currentTurn.board.lastStoneColor()?.name, currentTurn.board.previousStone()?.let { it.point.x to it.point.y })
            proceedTurn(inputPoint, onInappropriate)
        }
        afterGame(currentTurn.board.lastStoneColor()?.name)
    }

    fun isSameTurn() = previousTurn == currentTurn

    fun isFinished() = currentTurn is Finished

    fun proceedTurn(
        inputPoint: () -> Pair<Int, Int>,
        onInappropriate: (String) -> Unit,
    ) {
        previousTurn = currentTurn
        val point = inputPoint()
        currentTurn = currentTurn.placeStone(Point(point.first, point.second), onInappropriate)
    }

    companion object {
        fun of(
            stoneColor: StoneColor?,
            stones: Set<Stone> = setOf(),
        ): OmokGame {
            return when (stoneColor) {
                StoneColor.BLACK -> OmokGame(BlackTurn(Board(stones)))
                StoneColor.WHITE -> OmokGame(WhiteTurn(Board(stones)))
                else -> OmokGame(BlackTurn(Board(stones)))
            }
        }
    }
}
