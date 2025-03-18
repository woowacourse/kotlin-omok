package omok.view

import omok.domain.StoneState

class InputView {
    fun getPosition(
        turn: StoneState,
        latestPosition: String,
    ): String {
        val message = MESSAGE_TURN.format(turn.getDisplayColor())
        if (latestPosition.isNotEmpty()) {
            println(message + MESSAGE_LATEST_POSITION.format(latestPosition))
        } else {
            println(message)
        }

        print(MESSAGE_POSITION_GUIDE)
        return readln().trim()
    }

    private fun StoneState.getDisplayColor(): String {
        return when (this) {
            StoneState.BLACK -> "흑"
            StoneState.WHITE -> "백"
            else -> throw IllegalArgumentException()
        }
    }

    companion object {
        private const val MESSAGE_TURN: String = "%s의 차례입니다."
        private const val MESSAGE_LATEST_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_POSITION_GUIDE: String = "위치를 입력하세요: "
    }
}
