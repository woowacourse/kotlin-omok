package omok.view

import omok.domain.board.StoneStatus

class InputView {
    fun readStoneWithLastPosition(
        turn: StoneStatus,
        lastPosition: String?,
    ): String {
        while (true) {
            print(MESSAGE_PLAYER_TURN.format(turn.toKorean()))
            println(MESSAGE_INPUT_POSITION)
            lastPosition?.let {
                println(MESSAGE_LAST_POSITION.format(lastPosition))
            }
            val input = readlnOrNull()?.trim()

            if (!input.isNullOrEmpty()) {
                return input
            }
            println(MESSAGE_EMPTY_INPUT)
        }
    }

    private fun StoneStatus.toKorean(): String {
        return when (this) {
            StoneStatus.BLACK -> "흑"
            StoneStatus.WHITE -> "백"
            else -> throw IllegalStateException()
        }
    }

    companion object {
        private const val MESSAGE_PLAYER_TURN = "%s의 차례입니다."
        private const val MESSAGE_INPUT_POSITION = "위치를 입력하세요: "
        private const val MESSAGE_LAST_POSITION = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_EMPTY_INPUT = "빈 값을 입력하셨습니다. 다시 입력해주세요."
    }
}
