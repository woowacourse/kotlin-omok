package omok.view

import omok.model.Position
import omok.model.Stone

class InputView {
    fun readStonePosition(
        stone: Stone,
        recentPosition: Position?,
    ): Position {
        print(POSITION_ORDER_MESSAGE.format(stone.output()))
        recentPosition?.run { println(LAST_POSITION_MESSAGE.format(this.output())) } ?: println()
        print(INPUT_POSITION_MESSAGE)
        return readln().toPosition()
    }

    companion object {
        private const val POSITION_ORDER_MESSAGE = "%s의 차례입니다. "
        private const val LAST_POSITION_MESSAGE = "(마지막 돌의 위치: %s)"
        private const val INPUT_POSITION_MESSAGE = "위치를 입력하세요: "
    }
}
