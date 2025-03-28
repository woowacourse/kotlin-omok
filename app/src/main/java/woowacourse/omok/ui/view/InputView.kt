package woowacourse.omok.ui.view

import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.StoneColor

class InputView {
    fun askForPosition(
        currentTurnColor: StoneColor,
        position: Position?,
    ): Position {
        print(SHOW_PLAYER_TURN.format(currentTurnColor.toColumnLabel()))
        position?.let { print(LAST_STONE_POSITION.format(position.toColumnLabel())) }
        print(INPUT_POSITION_MESSAGE.format(currentTurnColor.toColumnLabel()))
        return readln().toValidPosition() ?: askForPosition(currentTurnColor, position)
    }

    private fun String.toValidPosition(): Position? {
        if (this.isBlank()) return null

        val columnPosition = this.first().toColumnPosition()
        val rowPosition = this.substring(1).toIntOrNull()

        return rowPosition?.let { Position(it, columnPosition) }
    }

    private fun Char.toColumnPosition(): Int {
        val alphabets = ALPHABETS.toList()
        return alphabets.indexOf(this) + 1
    }

    private fun StoneColor.toColumnLabel(): String =
        when (this) {
            StoneColor.BLACK -> BLACK_COLOR_LABEL
            StoneColor.WHITE -> WHITE_COLOR_LABEL
        }

    private fun Position.toColumnLabel(): String = "${this.column.toColumnLabel()}${this.row}"

    private fun Int.toColumnLabel(): Char {
        val alphabets = ALPHABETS.toList()
        return alphabets[this - 1]
    }

    companion object {
        private const val SHOW_PLAYER_TURN: String = "\n%s의 차례입니다."
        private const val LAST_STONE_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val INPUT_POSITION_MESSAGE: String = "\n위치를 입력하세요: "
        private const val BLACK_COLOR_LABEL: String = "흑"
        private const val WHITE_COLOR_LABEL: String = "백"
        private val ALPHABETS: CharRange = ('A'..'Z')
    }
}
