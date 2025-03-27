package woowacourse.omok.domain.service

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.exception.ResultState
import woowacourse.omok.domain.exception.execute
import woowacourse.omok.domain.point.OmokPoints
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.ui.event.GameEventListener

class OmokGame(
    private val omokBoard: OmokBoard,
    private val event: GameEventListener,
) {
    private var stone = StoneColor.BLACK

    fun play(point: Point) {
        val newStone = point.copy(status = BoardStatus.Moved(stone))
        when (val result = canMove(newStone)) {
            is ResultState.Error -> {
                event.onFailToAddStone(result.exceptions)
            }

            is ResultState.Success -> {
                omokBoard.addStone(newStone)
                event.onPlacedStone(stone)
                checkOmok(newStone)
            }
        }
    }

    fun getMovedStone() = omokBoard.getMovedPoints()

    private fun checkOmok(point: Point) {
        if (omokBoard.isOmok(point)) {
            event.onFinishedGame(stone)
        }
        stone = stone.toggle()
    }

    private fun canMove(point: Point) =
        execute {
            omokBoard.pointValidation(point)
        }

    companion object {
        fun create(event: GameEventListener): OmokGame {
            val points = OmokPoints()
            val omokBoard = OmokBoard(points)
            val game = OmokGame(omokBoard, event)
            return game
        }
    }
}
