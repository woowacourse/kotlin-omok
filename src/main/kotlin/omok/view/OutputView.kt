package omok.view

import omok.model.*

object OutputView {
    private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
    private const val MESSAGE_OMOK_TURN = "\n%s의 차례입니다. "
    private const val MESSAGE_BEFORE_STONE = "(마지막 돌의 위치: %c%d)"
    private val boardTable: MutableList<MutableList<Char>> =
        mutableListOf(
            mutableListOf('┌', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┬', '┐'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('├', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┼', '┤'),
            mutableListOf('└', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┴', '┘'),
        )
    private val boardForm =
        listOf(
            " 15 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 14 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 13 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 12 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 11 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            " 10 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  9 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  8 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  7 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  6 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  5 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  4 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  3 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  2 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "  1 %c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c──%c",
            "    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O",
        )

    fun printGameStart() {
        println(MESSAGE_OMOK_START)
    }

    private fun convertStoneIcon(stoneType: StoneType): Char {
        return when (stoneType) {
            StoneType.WHITE -> '○'
            StoneType.BLACK -> '●'
        }
    }

    fun printBoard(board: Board) {
        boardTable.indices.forEach {
            println(boardForm[it].format(*generatePrintedLine(it, board.board[it]).toTypedArray()))
        }
        println(boardForm.last())
    }

    private fun generatePrintedLine(
        lineIndex: Int,
        stoneTypes: List<StoneType?>,
    ): List<Char> {
        return boardTable[lineIndex].mapIndexed { columnIdx, char ->
            stoneTypes[columnIdx]?.let { convertStoneIcon(it) } ?: char
        }
    }

    fun printTurn(turn: Turn) {
        println(generateTurnMessage(turn))
    }

    private fun generateTurnMessage(turn: Turn): String {
        return when (turn) {
            is BlackTurn -> {
                MESSAGE_OMOK_TURN.format("흑") + (turn.before?.let { generateBeforeMessage(it) } ?: "")
            }

            is WhiteTurn -> {
                MESSAGE_OMOK_TURN.format("백") + generateBeforeMessage(turn.before)
            }

            is FinishedTurn -> "게임이 종료되었습니다."
        }
    }

    private fun generateBeforeMessage(stone: Stone): String {
        val row = (stone.point.row + 65).toChar()
        val column = 15 - stone.point.column
        return MESSAGE_BEFORE_STONE.format(row, column)
    }

    fun printDuplicatedPointMessage() {
        println("해당 위치 좌표에 이미 돌이 착수되어 있습니다. 다시 입력해주세요.")
    }
}
