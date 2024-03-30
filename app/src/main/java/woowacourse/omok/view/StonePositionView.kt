package woowacourse.omok.view

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

class StonePositionView {
    fun read(
        boardSize: Int,
        nowOrderStone: Stone,
        recentPosition: Position?,
    ): Position {
        print(POSITION_ORDER_MESSAGE.format(nowOrderStone.output()))
        recentPosition?.run { println(LAST_POSITION_MESSAGE.format(this.output(boardSize))) } ?: println()
        print(INPUT_POSITION_MESSAGE)
        return readln().toPosition(boardSize)
    }

    companion object {
        private const val POSITION_ORDER_MESSAGE = "%s의 차례입니다. "
        private const val LAST_POSITION_MESSAGE = "(마지막 돌의 위치: %s)"
        private const val INPUT_POSITION_MESSAGE = "위치를 입력하세요: "
    }
}
