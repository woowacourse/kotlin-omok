package omok.view

import omok.model.domain.omokboard.ColumnPosition
import omok.model.domain.omokboard.Position
import omok.model.domain.omokboard.RowPosition
import omok.model.domain.player.StoneColor

class InputView {
    fun askForPosition(
        currentTurnColor: StoneColor,
        position: Position?,
    ): Position {
        print(SHOW_PLAYER_TURN.format(currentTurnColor.toLabel()))
        position?.let { print(LAST_STONE_POSITION.format(position.toLabel())) }
        print(INPUT_POSITION_MESSAGE.format(currentTurnColor.toLabel()))
        return validatePositionInput(readln()) ?: askForPosition(currentTurnColor, position)
    }

    companion object {
        private const val SHOW_PLAYER_TURN: String = "\n%s의 차례입니다."
        private const val LAST_STONE_POSITION: String = "(마지막 돌의 위치: %s)"
        private const val INPUT_POSITION_MESSAGE: String = "\n위치를 입력하세요: "

        private fun StoneColor.toLabel(): String =
            when (this) {
                StoneColor.BLACK -> "흑"
                StoneColor.WHITE -> "백"
            }

        private fun Position.toLabel(): String = "${this.column.toLabel()}${this.row}"

        private fun ColumnPosition.toLabel(): Char {
            val alphabets = ('A'..'Z').toList()
            return alphabets[this.value - 1]
        }

        private fun validatePositionInput(input: String): Position? =
            when {
                input.isBlank() -> null
                else -> input.toPosition()
            }

        private fun String.toPosition(): Position {
            val columnPosition = this[0].toColumnPosition()
            val rowPosition = RowPosition(this.substring(1).toInt())

            return Position(rowPosition, columnPosition)
        }

        private fun Char.toColumnPosition(): ColumnPosition {
            val alphabets = ('A'..'Z').toList()
            return ColumnPosition(alphabets.indexOf(this) + 1)
        }
    }
}
