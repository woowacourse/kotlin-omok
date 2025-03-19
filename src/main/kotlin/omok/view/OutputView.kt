package omok.view

import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.board.Point
import omok.domain.board.StoneStatus

class OutputView {
    fun printBoard(board: List<Point>) {
        val currentBoard = board.toList()
        val boardMap =
            StringBuilder(
                """
                15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐ 
                14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                9  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                8  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                7  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                6  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                5  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                4  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                3  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                2  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤ 
                1  └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘ 
                   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
                """.trimIndent(),
            )

        board
            .forEach {
                val strIndex = (it.x.value) * 3 + (it.y.value - 1) * 45
                val stone =
                    when (it.stoneStatus) {
                        StoneStatus.BLACK -> '●'
                        StoneStatus.WHITE -> '○'
                        StoneStatus.EMPTY -> boardMap[strIndex]
                    }

                boardMap.setCharAt(strIndex, stone)
            }
        println(boardMap)
    }

    private fun OmokRow.toBoarder(): String {
        return when (this) {
            OmokRow.ONE -> "$this └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘" + getFormattedColumn()
            OmokRow.FIFTEEN -> "$this ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐"
            else -> "$this ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤"
        }
    }

    private fun getFormattedColumn(): String {
        val formatted = OmokColumn.entries.map { it.toString() }
        return "\n   " + formatted.joinToString("") { "$it  " }
    }
}

fun main() {
    val board = OmokBoard()
    board.addStone(Point(OmokColumn.O, OmokRow.TEN, StoneStatus.BLACK))
    board.addStone(Point(OmokColumn.H, OmokRow.TEN, StoneStatus.BLACK))

    OutputView().printBoard(
        board.board,
    )
}
