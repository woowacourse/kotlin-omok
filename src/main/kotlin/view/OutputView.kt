package view

import domain.Color
import domain.turn.State

object OutputView {
    private const val MESSAGE_START = "오목 게임을 시작합니다."
    private const val ROW_SIZE = 47
    private const val COLUMN_SIZE = 3
    private const val BLACK_STONE = '●'
    private const val WHITE_STONE = '○'

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
    }

    fun printOmokState(blackTurnState: State, whiteTurnState: State) {
        val stringBuilder = StringBuilder(default)
        val resultBuilder = addStones(addStones(stringBuilder, blackTurnState, Color.BLACK), whiteTurnState, Color.WHITE)
        println(resultBuilder)
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
}
