package omok.view

import omok.model.domain.omokboard.ColumnPosition
import omok.model.domain.omokboard.OmokBoard
import omok.model.domain.omokboard.PointState
import omok.model.domain.omokboard.Position
import omok.model.domain.omokboard.RowPosition

class OutputView {
    fun displayOmokGameStart() {
        println(START_OMOK_GAME_TITLE)
    }

    fun displayOmokBoard(omokBoard: OmokBoard) {
        val width = omokBoard.value.keys.maxOf { it.column.value }
        val height = omokBoard.value.keys.maxOf { it.row.value }

        (1..height)
            .reversed()
            .forEach { rowNumber ->
                displayRow(omokBoard, RowPosition(rowNumber))
            }

        displayColumnLabels(width)
    }

    private fun displayRow(
        omokBoard: OmokBoard,
        rowPosition: RowPosition,
    ) {
        val rowPoints = omokBoard.value.filter { it.key.row == rowPosition }
        print(" ${String.format("%02d", rowPosition.value)} ")
        println(rowPoints.keys.map { it.draw(omokBoard) }.joinToString { "──" })
    }

    private fun Position.draw(omokBoard: OmokBoard): String {
        val width = omokBoard.value.keys.maxOf { it.column.value }
        val height = omokBoard.value.keys.maxOf { it.row.value }

        val stoneColor = omokBoard.value[this]?.state ?: PointState.EMPTY

        return when {
            stoneColor == PointState.OCCUPIED_BLACK -> "●"
            stoneColor == PointState.OCCUPIED_WHITE -> "○"
            this.row.value == width && this.column.value == 1 -> "┌"
            this.row.value == 1 && this.column.value == height -> "┐"
            this.row.value == width && this.column.value == 1 -> "└"
            this.row.value == 1 && this.column.value == height -> "┘"
            this.column.value == 1 -> "├"
            this.column.value == width -> "┤"
            this.row.value == 1 -> "┬"
            this.row.value == height -> "┴"
            else -> "┼"
        }
    }

    private fun displayColumnLabels(boardWidth: Int) {
        println(
            "  ${
                (1..boardWidth).map { columnNumber ->
                    ColumnPosition(columnNumber)
                        .toEnglish()
                }.joinToString { "  " }
            }  ",
        )
    }

    companion object {
        private const val START_OMOK_GAME_TITLE: String = "오목 게임을 시작합니다."
    }
}
