package woowacourse.omok.domain.view

import woowacourse.omok.domain.model.BlackTurn
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.FinishedTurn
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType
import woowacourse.omok.domain.model.Turn
import woowacourse.omok.domain.model.WhiteTurn

object OutputView {
    private const val MESSAGE_GAME_START = "오목 게임을 시작합니다."
    private const val MESSAGE_TURN = "%s의 차례입니다. "
    private const val MESSAGE_BEFORE_STONE = "(마지막 돌의 위치: %c%d)"
    const val MESSAGE_GAME_END = "게임이 종료 되었습니다."
    private const val STONE_TYPE_BLACK = "흑"
    private const val STONE_TYPE_WHITE = "백"
    private const val STONE_ICON_BLACK = '●'
    private const val STONE_ICON_WHITE = '○'

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
        boardTable.indices.forEach {
            println(
                boardForm[it].format(
                    *generatePrintedLine(
                        it,
                        board.board[14 - it],
                    ).toTypedArray(),
                ),
            )
        }
        println(boardForm.last())
    }

    private fun generatePrintedLine(
        lineIndex: Int,
        stoneTypes: List<StoneType>,
    ): List<Char> {
        return List(boardTable[lineIndex].size) { columnIdx ->
            convertStoneIcon(stoneTypes[columnIdx], lineIndex, columnIdx)
        }
    }

    fun printTurn(
        turn: Turn,
        stone: Stone?,
    ) {
        println(generateTurnMessage(turn) + (stone?.let { generateBeforeMessage(it) } ?: ""))
    }

    fun generateTurnMessage(turn: Turn): String =
        when (turn) {
            is BlackTurn -> MESSAGE_TURN.format(STONE_TYPE_BLACK)
            is WhiteTurn -> MESSAGE_TURN.format(STONE_TYPE_WHITE)
            is FinishedTurn -> "$MESSAGE_GAME_END ${generateStoneTypeMessage(turn.beforeStone.type)}의 승리!"
        }

    private fun generateStoneTypeMessage(stoneType: StoneType): String =
        when (stoneType) {
            StoneType.BLACK -> STONE_TYPE_BLACK
            StoneType.WHITE -> STONE_TYPE_WHITE
            StoneType.EMPTY -> ""
        }

    private fun generateBeforeMessage(stone: Stone): String {
        return MESSAGE_BEFORE_STONE.format(stone.point.x + 65, stone.point.y + 1)
    }
}
