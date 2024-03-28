package woowacourse.omok.domain.view

import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.model.WhiteTurn

object OutputView {
    const val MESSAGE_GAME_START = "오목 게임을 시작합니다. "
    const val MESSAGE_TURN = "%s의 차례입니다. "
    const val MESSAGE_BEFORE_POINT = "(마지막 돌의 위치: %c%d)"
    const val MESSAGE_WINNER = "%s돌이 승리했습니다!!"
    const val MESSAGE_INVALID_POINT_INPUT = "잘못된 위치 좌표입니다. 재입력 해주세요."
    const val MESSAGE_GAME_END = "게임이 종료되었습니다.\n재시작 버튼을 클릭해 게임을 재시작해주세요."
    const val STONE_TYPE_BLACK = "흑"
    const val STONE_TYPE_WHITE = "백"
    private const val STONE_ICON_BLACK = '●'
    private const val STONE_ICON_WHITE = '○'

    private lateinit var boardTable: MutableList<MutableList<Char>>

    private lateinit var boardForm: List<String>

    private fun generateBoardForm(boardSize: Int): List<String> {
        val formattedLines = mutableListOf<String>()
        for (i in boardSize downTo 1) {
            val line =
                buildString {
                    if (i < 10) append("  $i ") else append(" $i ")
                    for (j in 1..boardSize) {
                        append("%c──")
                    }
                }
            formattedLines.add(line.dropLast(2))
        }
        formattedLines.add("    " + ('A' until ('A' + boardSize)).joinToString("  "))
        return formattedLines
    }

    private fun generateBoardTable(boardSize: Int): MutableList<MutableList<Char>> {
        val table = mutableListOf<MutableList<Char>>()
        table.add(mutableListOf('┌'))
        repeat(boardSize - 2) {
            table[0].add('┬')
        }
        table[0].add('┐')

        repeat(boardSize - 2) {
            val row = mutableListOf<Char>()
            row.add('├')
            repeat(boardSize - 2) {
                row.add('┼')
            }
            row.add('┤')
            table.add(row)
        }

        table.add(mutableListOf('└'))
        repeat(boardSize - 2) {
            table[boardSize - 1].add('┴')
        }
        table[boardSize - 1].add('┘')

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
        boardTable = generateBoardTable(board.size)
        boardForm = generateBoardForm(board.size)

        repeat(board.size) {
            println(
                boardForm[it].format(
                    *generatePrintedLine(
                        it,
                        board.getBoardLine(it + 1)
                    ).toTypedArray()
                )
            )
        }
        println(boardForm.last())
    }

    private fun generatePrintedLine(
        lineIndex: Int,
        stoneTypes: List<StoneType>,
    ): List<Char> {
        return List(stoneTypes.size) { columnIdx ->
            convertStoneIcon(stoneTypes[columnIdx], lineIndex, columnIdx)
        }
    }

    fun printTurn(
        turn: Turn,
        beforeStone: Stone?,
    ) {
        println(generateTurnMessage(turn, beforeStone))
    }

    fun printWinner(board: Board) {
        val winner = generateWinnerMessage(board.beforeStone?.type)
        println(MESSAGE_WINNER.format(winner))
    }

    fun generateWinnerMessage(stoneType: StoneType?): String {
        return when (stoneType) {
            StoneType.BLACK -> STONE_TYPE_BLACK
            StoneType.WHITE -> STONE_TYPE_WHITE
            else -> ""
        }
    }

    fun generateTurnMessage(
        turn: Turn,
        beforeStone: Stone?,
    ): String {
        return when (turn) {
            is BlackTurn -> {
                MESSAGE_TURN.format(STONE_TYPE_BLACK) + (beforeStone?.let { stone ->
                    generateBeforePointMessage(stone.point)
                } ?: "")
            }

            is WhiteTurn -> {
                MESSAGE_TURN.format(STONE_TYPE_WHITE) + (beforeStone?.let { stone ->
                    generateBeforePointMessage(stone.point)
                } ?: "")
            }

            is FinishedTurn -> ""
        }
    }

    private fun generateBeforePointMessage(point: Point): String {
        return MESSAGE_BEFORE_POINT.format(point.x + 65, point.y + 1)
    }

    fun printInvalidPointInputMessage() {
        println(MESSAGE_INVALID_POINT_INPUT)
    }
}
