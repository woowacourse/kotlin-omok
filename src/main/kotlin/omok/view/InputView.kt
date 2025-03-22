package omok.view

import omok.domain.stone.LatestStone
import omok.domain.stone.StoneColor
import omok.view.ext.toLabel

class InputView {
    fun readStoneWithLatestStone(
        stoneColor: StoneColor,
        lastStone: LatestStone,
    ): String {
        printTurnMessage(stoneColor, lastStone)
        val input = readlnOrNull()?.trim()
        return validateInput(input) ?: readStoneWithLatestStone(stoneColor, lastStone)
    }

    private fun printTurnMessage(
        stoneColor: StoneColor,
        lastStone: LatestStone,
    ) {
        print(MESSAGE_PLAYER_TURN.format(stoneColor.toLabel()))
        if (lastStone.value.isNotBlank()) {
            println(MESSAGE_LAST_POSITION.format(lastStone.value))
        }
        print(MESSAGE_INPUT_POSITION)
    }

    private fun validateInput(input: String?): String? {
        return if (!input.isNullOrEmpty()) {
            input
        } else {
            println(MESSAGE_EMPTY_INPUT)
            null
        }
    }

    companion object {
        private const val MESSAGE_PLAYER_TURN = "%s의 차례입니다."
        private const val MESSAGE_INPUT_POSITION = "위치를 입력하세요: "
        private const val MESSAGE_LAST_POSITION = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_EMPTY_INPUT = "빈 값을 입력하셨습니다. 다시 입력해주세요."
    }
}
