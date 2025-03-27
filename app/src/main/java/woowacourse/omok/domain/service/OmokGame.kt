package woowacourse.omok.domain.service

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.exception.ResultState
import woowacourse.omok.domain.exception.execute
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.ui.event.GameEventListener

class OmokGame(
    private val omokBoard: OmokBoard,
    private val event: GameEventListener,
) {
    private var currentTurn: StoneColor = StoneColor.BLACK

    fun setTurn(color: StoneColor) {
        currentTurn = color
    }

    fun play(point: Point) {
        val newStone = point.copy(status = BoardStatus.Moved(currentTurn))
        when (val result = canMove(newStone)) {
            is ResultState.Error -> {
                event.onFailToAddStone(result.exceptions)
            }

            is ResultState.Success -> {
                omokBoard.addStone(newStone)
                event.onPlacedStone(currentTurn)
                checkOmok(newStone)
            }
        }
    }

    fun getMovedStone() = omokBoard.getMovedPoints()

    fun combine(point: List<Point>) = omokBoard.combine(point)

    private fun checkOmok(point: Point) {
        if (omokBoard.isOmok(point)) {
            event.onFinishedGame(currentTurn)
        }
        currentTurn = currentTurn.toggle()
    }

    private fun canMove(point: Point) =
        execute {
            omokBoard.pointValidation(point)
        }
}
