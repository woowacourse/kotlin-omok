package omok.view

import omok.domain.board.Board
import omok.domain.board.Column
import omok.domain.board.Position
import omok.domain.board.Row
import omok.model.toPresentation

class OutputView {
    fun printStart() {
        println("오목 게임을 시작합니다.")
    }

    fun printBoard(board: Board) {
        println(getBoardPresentation(board))
    }

    private fun getBoardPresentation(board: Board): String {
        val rowNumber = mutableListOf(15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
        val columnAlphabet = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")

        return """
            |
            |${Row.values().reversed().joinToString("\n") { row ->
            " %2d ".format(rowNumber.removeFirst()) +
                Column.values().joinToString("──") {column ->
                    val position = Position(column, row)
                    position.toPresentation(board.positions[position])
                }
        }}
            |    ${columnAlphabet.joinToString("  ")}
        """.trimMargin()
    }
}
