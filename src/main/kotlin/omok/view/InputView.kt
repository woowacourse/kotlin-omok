package omok.view

import omok.domain.omokboard.Position
import omok.domain.player.StoneColor
import omok.domain.player.StoneColor.BLACK
import omok.domain.player.StoneColor.WHITE

class InputView {
    fun askForPosition(
        currentTurnColor: StoneColor,
        position: Position?,
    ): Position {
        print(SHOW_PLAYER_TURN.format(currentTurnColor.toLabel()))
        position?.let { print(LAST_STONE_POSITION.format(position)) }
        print(INPUT_POSITION_MESSAGE.format(currentTurnColor.toLabel()))
        return readln().toValidPosition() ?: askForPosition(currentTurnColor, position)
    }

    private fun StoneColor.toLabel(): String =
        when (this) {
            BLACK -> BLACK_COLOR_LABEL
            WHITE -> WHITE_COLOR_LABEL
        }

    private fun String.toValidPosition(): Position? {
        if (this.isBlank()) return null

        val rowPosition = this.substring(1).toIntOrNull()

        return rowPosition?.let { Position(this) }
    }

    companion object {
        private const val SHOW_PLAYER_TURN: String = "\n%s의 차례입니다."
        private const val LAST_STONE_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val INPUT_POSITION_MESSAGE: String = "\n위치를 입력하세요: "
        private const val BLACK_COLOR_LABEL: String = "흑"
        private const val WHITE_COLOR_LABEL: String = "백"
    }
}
