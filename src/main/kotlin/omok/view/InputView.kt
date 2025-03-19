package omok.view

import omok.model.domain.omokboard.Position
import omok.model.domain.player.StoneColor

class InputView {
    fun askForPosition(
        currentTurnColor: StoneColor,
        position: Position?,
    ): Position {
        println(SHOW_PLAYER_TURN.format(currentTurnColor.toKorean()))
        position?.let { println(LAST_STONE_POSITION.format(position.toText())) }
        print(INPUT_POSITION_MESSAGE.format(currentTurnColor.toKorean()))
        return validatePositionInput(readln()) ?: askForPosition(currentTurnColor, position)
    }

    companion object {
        private const val SHOW_PLAYER_TURN: String = "%s의 차례입니다."
        private const val LAST_STONE_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val INPUT_POSITION_MESSAGE: String = "위치를 입력하세요: "

        private fun validatePositionInput(input: String): Position? =
            when {
                input.isBlank() -> null
                else -> input.toPosition()
            }
    }
}
