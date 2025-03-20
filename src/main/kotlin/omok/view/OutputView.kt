package omok.view

import omok.model.domain.omokboard.ColumnPosition
import omok.model.domain.omokboard.OmokBoard
import omok.model.domain.omokboard.PointState
import omok.model.domain.omokboard.Position
import omok.model.domain.omokboard.RowPosition
import omok.model.domain.rule.GameResult
import omok.model.domain.rule.PlaceResult

class OutputView {
    fun displayOmokGameStart() {
        println(START_OMOK_GAME_TITLE)
    }

    fun displayOmokBoard(omokBoard: OmokBoard) {
        (1..omokBoard.height)
            .reversed()
            .forEach { rowNumber ->
                displayRow(omokBoard, RowPosition(rowNumber))
            }

        displayColumnLabels(omokBoard.width)
    }

    private fun displayRow(
        omokBoard: OmokBoard,
        rowPosition: RowPosition,
    ) {
        val rowPoints = omokBoard.value.filter { it.key.row == rowPosition }
        print(" ${String.format("%02d", rowPosition.value)} ")
        println(rowPoints.keys.joinToString("──") { it.drawBoard(omokBoard) })
    }

    fun displayErrorMessage(placeResult: PlaceResult) {
        println()
        println(
            when (placeResult) {
                PlaceResult.Failure.AlreadyExistStone -> ALREADY_EXIST_MESSAGE
                PlaceResult.Failure.InvalidPosition -> INVALID_POSITION_MESSAGE
                PlaceResult.Failure.DoubleThreeViolation -> FORBIDDEN_DOUBLE_THREE
                PlaceResult.Failure.DoubleFourViolation -> FORBIDDEN_DOUBLE_FOUR
                PlaceResult.Failure.OverlineViolation -> FORBIDDEN_OVERLINE
                else -> return
            },
        )
    }

    fun displayWinningMessage(gameResult: GameResult) {
        println()
        println(
            when (gameResult) {
                GameResult.DRAW -> DRAW_RESULT_MESSAGE
                else -> WIN_RESULT_MESSAGE.format(gameResult.toLabel())
            },
        )
    }

    companion object {
        private const val START_OMOK_GAME_TITLE: String = "오목 게임을 시작합니다."
        private const val ALREADY_EXIST_MESSAGE: String = "이미 돌이 있는 자리에 둘 수 없습니다."
        private const val INVALID_POSITION_MESSAGE: String = "잘못된 위치 입니다."
        private const val FORBIDDEN_DOUBLE_THREE: String = "3 x 3은 금지입니다."
        private const val FORBIDDEN_DOUBLE_FOUR: String = "4 x 4는 금지입니다."
        private const val FORBIDDEN_OVERLINE: String = "6목은 금지입니다."
        private const val DRAW_RESULT_MESSAGE: String = "무승부 입니다."
        private const val WIN_RESULT_MESSAGE: String = "%s의 우승을 축하드립니다!"
        private const val BLACK_COLOR_LABEL: String = "흑"
        private const val WHITE_COLOR_LABEL: String = "백"

        private fun Position.drawBoard(omokBoard: OmokBoard): String {
            val stoneColor = omokBoard.value[this]?.state ?: PointState.EMPTY

            return when {
                stoneColor == PointState.OCCUPIED_BLACK -> "●"
                stoneColor == PointState.OCCUPIED_WHITE -> "○"
                this.row.value == omokBoard.height && this.column.value == 1 -> "┌"
                this.row.value == omokBoard.height && this.column.value == omokBoard.width -> "┐"
                this.row.value == 1 && this.column.value == 1 -> "└"
                this.row.value == 1 && this.column.value == omokBoard.width -> "┘"
                this.column.value == 1 -> "├"
                this.column.value == omokBoard.width -> "┤"
                this.row.value == 1 -> "┴"
                this.row.value == omokBoard.height -> "┬"
                else -> "┼"
            }
        }

        private fun displayColumnLabels(boardWidth: Int) {
            println(
                (1..boardWidth)
                    .map { columnNumber ->
                        ColumnPosition(columnNumber)
                            .toLabel()
                    }.joinToString(separator = "  ", prefix = "    "),
            )
        }

        private fun ColumnPosition.toLabel(): Char {
            val alphabets = ('A'..'Z').toList()
            return alphabets[this.value - 1]
        }

        private fun GameResult.toLabel(): String =
            when (this) {
                GameResult.WIN_BLACK -> BLACK_COLOR_LABEL
                GameResult.WIN_WHITE -> WHITE_COLOR_LABEL
                else -> ""
            }
    }
}
