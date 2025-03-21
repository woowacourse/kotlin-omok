package omok.view

import omok.domain.stone.LatestStone
import omok.domain.stone.StoneColor
import omok.view.ext.toLabel

class InputView {
    fun readStoneWithLatestStone(
        stoneColor: StoneColor,
        lastStone: LatestStone,
    ): String {
        while (true) {
            print(MESSAGE_PLAYER_TURN.format(stoneColor.toLabel()))
            if (lastStone.value.isNotBlank()) {
                println(MESSAGE_LAST_POSITION.format(lastStone.value))
            }
            print(MESSAGE_INPUT_POSITION)
            val input = readlnOrNull()?.trim()

            if (!input.isNullOrEmpty()) {
                return input
            }
            println(MESSAGE_EMPTY_INPUT)
        }
    }

    companion object {
        private const val MESSAGE_PLAYER_TURN = "%s의 차례입니다."
        private const val MESSAGE_INPUT_POSITION = "위치를 입력하세요: "
        private const val MESSAGE_LAST_POSITION = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_EMPTY_INPUT = "빈 값을 입력하셨습니다. 다시 입력해주세요."
    }
}
