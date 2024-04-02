package woowacourse.omok.domain.view

import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone

class InputView {
    fun readStonePosition(
        stone: Stone,
        recentPosition: Position?,
    ): Position {
        print("${stone.output()}의 차례입니다. ")
        recentPosition?.run { println("(마지막 돌의 위치: ${this.output()})") } ?: println()
        print("위치를 입력하세요: ")
        return readln().toPosition()
    }

    fun readFirstStonePosition(stone: Stone): Position {
        print("${stone.output()}의 차례입니다. ")
        print("위치를 입력하세요: ")
        return readln().toPosition()
    }
}
