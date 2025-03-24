package omok.view

import omok.domain.omokboard.ColumnPosition
import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.Position
import omok.domain.omokboard.RowPosition
import omok.domain.omokboard.State
import omok.domain.placeresult.InvalidMove.AlreadyExistStone
import omok.domain.placeresult.InvalidMove.InvalidPosition
import omok.domain.placeresult.PlaceResult
import omok.domain.rule.GameResult
import omok.domain.rule.GameResult.DRAW
import omok.domain.rule.GameResult.WIN_BLACK
import omok.domain.rule.GameResult.WIN_WHITE
import rule.type.Violation

class OutputView {
    fun displayOmokGameStart() {
        println(START_OMOK_GAME_TITLE)
    }

    fun displayOmokBoard(omokBoard: OmokBoard) {
        println()
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

    fun displayMisPlaceMessage(error: PlaceResult) {
        println()
        println(
            when (error) {
                AlreadyExistStone -> ALREADY_EXIST_MESSAGE
                InvalidPosition -> INVALID_POSITION_MESSAGE
                else -> return
            },
        )
    }

    fun displayForbiddenMessage(violation: Violation) {
        println()
        println(
            when (violation) {
                Violation.DOUBLE_THREE -> FORBIDDEN_DOUBLE_THREE
                Violation.DOUBLE_FOUR -> FORBIDDEN_DOUBLE_FOUR
                Violation.OVERLINE -> FORBIDDEN_OVERLINE
                else -> return
            },
        )
    }

    fun displayGameResultMessage(result: GameResult) {
        println()
        println(
            when (result) {
                DRAW -> DRAW_RESULT_MESSAGE
                else -> WIN_RESULT_MESSAGE.format(result.toLabel())
            },
        )
    }

    private fun Position.drawBoard(omokBoard: OmokBoard): String {
        val stoneColor = omokBoard.value[this]?.state ?: State.EMPTY

        return when {
            stoneColor == State.OCCUPIED_BLACK -> "●"
            stoneColor == State.OCCUPIED_WHITE -> "○"
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
                    ColumnPosition.toLabel(ColumnPosition(columnNumber))
                }.joinToString(separator = "  ", prefix = "    "),
        )
    }

    private fun GameResult.toLabel(): String =
        when (this) {
            WIN_BLACK -> BLACK_COLOR_LABEL
            WIN_WHITE -> WHITE_COLOR_LABEL
            else -> ""
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
    }
}
