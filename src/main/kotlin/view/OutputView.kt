package view

import domain.Color
import domain.Stone
import domain.turn.State

object OutputView {
    private const val MESSAGE_START = "오목 게임을 시작합니다."
    private const val MESSAGE_TURN = "%s의 차례입니다. (마지막 돌의 위치: %s)"
    private const val MESSAGE_BLACK_TURN = "흑의 차례입니다."
    private const val ROW_SIZE = 47
    private const val COLUMN_SIZE = 3
    private const val BLACK_STONE = '●'
    private const val WHITE_STONE = '○'
    private const val MESSAGE_WINNER = "%s 승"

    private val default = """
 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
    """.trimIndent()

    fun printStart() {
        println(MESSAGE_START)
        println(default)
        println(MESSAGE_BLACK_TURN)
    }

    fun printDuplicate() {
        println("해당 위치에 돌이 존재합니다.")
    }

    fun printOmokState(blackTurnState: State, whiteTurnState: State, nextColor: Color, stone: Stone) {
        val stringBuilder = StringBuilder(default)
        val resultBuilder =
            addStones(addStones(stringBuilder, blackTurnState, Color.BLACK), whiteTurnState, Color.WHITE)
        println(resultBuilder)
        println(MESSAGE_TURN.format(nextColor.value, stone.toString()))
    }

    private fun addStones(builder: StringBuilder, state: State, color: Color): StringBuilder {
        state.value.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, item ->
                if (item) {
                    builder.also {
                        it.setCharAt(
                            rowIndex * ROW_SIZE + COLUMN_SIZE + columnIndex * COLUMN_SIZE,
                            if (color == Color.WHITE) WHITE_STONE else BLACK_STONE
                        )
                    }
                }
            }
        }
        return builder
    }

    fun printWinner(color: Color) {
        println(MESSAGE_WINNER.format(color.value))
    }
}
