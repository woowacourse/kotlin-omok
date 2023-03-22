package view

import domain.state.BlackTurn
import domain.state.End
import domain.state.State
import domain.state.WhiteTurn
import domain.stone.Stone
import domain.stone.StoneType
import domain.stone.Stones

object OutputView {

    fun printOmokStart() {
        println(MESSAGE_OMOK_START)
        println()
        printBoard(Stones())
    }

    fun printBoard(stones: Stones) {
        setBoard(stones)

        board.indices.forEach {
            println(boardForm[it].format(*board[it].toTypedArray()))
        }
        println(boardForm[boardForm.lastIndex])
        println()
    }

    fun printTurn(state: State, stones: Stones) {
        val text: StringBuilder = StringBuilder()

        if (state !is End) text.append(MESSAGE_FORMAT_TURN.format(getTurnText(state)))
        if (state !is End && stones.values.isNotEmpty()) {
            text.append(MESSAGE_FORMAT_LAST_STONE_POSITION.format(positionToText(stones.values[stones.values.lastIndex])))
        }

        println(text)
    }

    fun printWinner(state: End) {
        println(MESSAGE_FORMAT_WINNER.format(getWinnerText(state)))
    }

    private fun setBoard(stones: Stones) {
        stones.values.forEach {
            if (getTypeEmoji(it.type) != null) board[(15 - it.point.col)][it.point.row - 1] = getTypeEmoji(it.type)!!
        }
    }

    private fun getTypeEmoji(stoneType: StoneType): Char? = when (stoneType) {
        StoneType.BLACK -> '●'
        StoneType.WHITE -> '○'
        else -> null
    }

    private fun getTurnText(state: State): String? = when (state) {
        is WhiteTurn -> "백"
        is BlackTurn -> "흑"
        else -> null
    }

    private fun getWinnerText(state: End): String? = when (state.getWinner()) {
        StoneType.BLACK -> "흑"
        StoneType.WHITE -> "백"
        else -> null
    }

    private fun positionToText(value: Stone): String {
        val xToString: String = (value.point.row.toChar() + 'A'.code - 1).toString()
        return "$xToString${value.point.col}"
    }

    private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
    private const val MESSAGE_FORMAT_TURN = "%s의 차례입니다. "
    private const val MESSAGE_FORMAT_LAST_STONE_POSITION = "(마지막 돌의 위치: %s)"
    private const val MESSAGE_FORMAT_WINNER = "%s돌이 우승했습니다."

    val board: MutableList<MutableList<Char>> = mutableListOf(
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
    val boardForm =
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
}
