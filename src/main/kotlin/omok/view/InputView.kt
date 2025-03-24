package omok.view

import omok.domain.omokboard.ColumnPosition
import omok.domain.omokboard.Position
import omok.domain.omokboard.RowPosition
import omok.domain.player.StoneColor
import omok.domain.player.StoneColor.BLACK
import omok.domain.player.StoneColor.WHITE

class InputView {
    fun askForPosition(
        currentTurnColor: StoneColor,
        position: Position?,
    ): Position {
        print(SHOW_PLAYER_TURN.format(currentTurnColor.toLabel()))
        position?.let { print(LAST_STONE_POSITION.format(position.toLabel())) }
        print(INPUT_POSITION_MESSAGE.format(currentTurnColor.toLabel()))
        return readln().toValidPosition() ?: askForPosition(currentTurnColor, position)
    }

    private fun StoneColor.toLabel(): String =
        when (this) {
            BLACK -> BLACK_COLOR_LABEL
            WHITE -> WHITE_COLOR_LABEL
        }

    private fun Position.toLabel(): String = "${this.column.toLabel()}${this.row}"

    private fun ColumnPosition.toLabel(): Char {
        val alphabets = ALPHABETS.toList()
        return alphabets[this.value - 1]
    }

    private fun String.toValidPosition(): Position? {
        if (this.isBlank()) return null

        val columnPosition = ColumnPosition.fromChar(this.first())
        val rowPosition = this.substring(1).toIntOrNull()

        return rowPosition?.let { Position(RowPosition(it), columnPosition) }
    }

    companion object {
        private const val SHOW_PLAYER_TURN: String = "\n%s의 차례입니다."
        private const val LAST_STONE_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val INPUT_POSITION_MESSAGE: String = "\n위치를 입력하세요: "
        private const val BLACK_COLOR_LABEL: String = "흑"
        private const val WHITE_COLOR_LABEL: String = "백"
        val ALPHABETS: CharRange = ('A'..'Z')
    }
}
