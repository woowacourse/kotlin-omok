package omok.view

import omok.model.BlackTurn
import omok.model.Board
import omok.model.FinishedTurn
import omok.model.Point
import omok.model.StoneType
import omok.model.Turn
import omok.model.WhiteTurn

object OutputView {
    private const val MESSAGE_GAME_START = "오목 게임을 시작합니다."
    private const val MESSAGE_TURN = "\n%s의 차례입니다. "
    private const val MESSAGE_BEFORE_POINT = "(마지막 돌의 위치: %c%d)"
    private const val MESSAGE_INVALID_POINT_INPUT = "\n잘못된 위치 좌표입니다. 재입력 해주세요."
    private const val STONE_TYPE_BLACK = "흑"
    private const val STONE_TYPE_WHITE = "백"
    private const val STONE_ICON_BLACK = '●'
    private const val STONE_ICON_WHITE = '○'

    private val boardTable: MutableList<MutableList<Char>> = generateBoardTable()

    private val boardForm = generateBoardForm()

    private fun generateBoardForm(): List<String> {
        val formattedLines = mutableListOf<String>()
        for (i in Board.BOARD_SIZE downTo 1) {
            val line =
                buildString {
                    if (i < 10) append("  $i ") else append(" $i ")
                    for (j in 1..Board.BOARD_SIZE) {
                        append("%c──")
                    }
                }
            formattedLines.add(line.dropLast(2))
        }
        formattedLines.add("    " + ('A' until ('A' + Board.BOARD_SIZE)).joinToString("  "))
        return formattedLines
    }

    private fun generateBoardTable(): MutableList<MutableList<Char>> {
        val table = mutableListOf<MutableList<Char>>()
        table.add(mutableListOf('┌'))
        repeat(Board.BOARD_SIZE - 2) {
            table[0].add('┬')
        }
        table[0].add('┐')

        repeat(Board.BOARD_SIZE - 2) {
            val row = mutableListOf<Char>()
            row.add('├')
            repeat(Board.BOARD_SIZE - 2) {
                row.add('┼')
            }
            row.add('┤')
            table.add(row)
        }

        table.add(mutableListOf('└'))
        repeat(Board.BOARD_SIZE - 2) {
            table[Board.BOARD_SIZE - 1].add('┴')
        }
        table[Board.BOARD_SIZE - 1].add('┘')

        return table
    }

    fun printGameStart() {
        println(MESSAGE_GAME_START)
    }

    private fun convertStoneIcon(
        stoneType: StoneType,
        column: Int,
        row: Int,
    ): Char {
        return when (stoneType) {
            StoneType.BLACK -> STONE_ICON_BLACK
            StoneType.WHITE -> STONE_ICON_WHITE
            StoneType.EMPTY -> boardTable[column][row]
        }
    }

    fun printBoard(board: Board) {
        repeat(Board.BOARD_SIZE) {
            println(boardForm[it].format(*generatePrintedLine(it, board.getPointStoneLine(it + 1)).toTypedArray()))
        }
        println(boardForm.last())
    }

    private fun generatePrintedLine(
        lineIndex: Int,
        stoneTypes: List<StoneType>,
    ): List<Char> {
        return List(Board.BOARD_SIZE) { columnIdx ->
            convertStoneIcon(stoneTypes[columnIdx], lineIndex, columnIdx)
        }
    }

    fun printTurn(
        turn: Turn,
        beforePoint: Point?,
    ) {
        println(generateTurnMessage(turn, beforePoint))
    }

    private fun generateTurnMessage(
        turn: Turn,
        beforePoint: Point?,
    ): String {
        return when (turn) {
            is BlackTurn -> {
                MESSAGE_TURN.format(STONE_TYPE_BLACK) + (beforePoint?.let { generateBeforePointMessage(it) } ?: "")
            }

            is WhiteTurn -> {
                MESSAGE_TURN.format(STONE_TYPE_WHITE) + (beforePoint?.let { generateBeforePointMessage(it) } ?: "")
            }

            is FinishedTurn -> {
                val winner =
                    when (turn.stoneType) {
                        StoneType.BLACK -> STONE_TYPE_BLACK
                        StoneType.WHITE -> STONE_TYPE_WHITE
                        StoneType.EMPTY -> ""
                    }
                "\n${winner}돌이 승리했습니다!!!"
            }
        }
    }

    private fun generateBeforePointMessage(point: Point): String {
        return MESSAGE_BEFORE_POINT.format(point.x + 65, point.y + 1)
    }

    fun printInvalidPointInputMessage() {
        println(MESSAGE_INVALID_POINT_INPUT)
    }
}
