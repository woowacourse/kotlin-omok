package view

import domain.OmokBoard
import domain.State
import domain.Stone

class OutputView : OutputViewInterface {
    companion object {
        private const val MESSAGE_START = "오목 게임을 시작합니다."
        private const val MESSAGE_TURN = "%s의 차례입니다. (마지막 돌의 위치: %s)"
        private const val MESSAGE_BLACK_TURN = "흑의 차례입니다."
        private const val MESSAGE_DUPLICATE = "해당 위치에 돌이 존재합니다."
        private const val MESSAGE_FORBIDDEN = "금수입니다."
        private const val MESSAGE_WINNER = "%s 승"
        private const val ROW_SIZE = 47
        private const val COLUMN_SIZE = 3
        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '◌'

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
    }

    override fun printStart() {
        println(MESSAGE_START)
        println(default)
        println(MESSAGE_BLACK_TURN)
    }

    override fun printDuplicate() {
        println(MESSAGE_DUPLICATE)
    }

    override fun printForbidden() {
        println(MESSAGE_FORBIDDEN)
    }

    override fun printOmokState(omokBoard: OmokBoard, nextState: State, stone: Stone) {
        val stringBuilder = StringBuilder(default)
        val resultBuilder = addStones(stringBuilder, omokBoard)
        println(resultBuilder)
        println(MESSAGE_TURN.format(nextState.toValue(), stone.toString()))
    }

    private fun addStones(builder: StringBuilder, omokBoard: OmokBoard): StringBuilder {
        omokBoard.value.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, item ->
                if (item != State.EMPTY) {
                    builder.also {
                        it.setCharAt(
                            rowIndex * ROW_SIZE + COLUMN_SIZE + columnIndex * COLUMN_SIZE,
                            if (item == State.WHITE) WHITE_STONE else BLACK_STONE
                        )
                    }
                }
            }
        }
        return builder
    }

    private fun State.toValue(): String? {
        return when (this) {
            State.BLACK -> StateUiModel.BLACK.value
            State.WHITE -> StateUiModel.WHITE.value
            else -> null
        }
    }

    override fun printWinner(state: State): State {
        println(MESSAGE_WINNER.format(state.toValue()))
        return state
    }
}
