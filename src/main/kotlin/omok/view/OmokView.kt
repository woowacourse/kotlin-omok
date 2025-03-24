package omok.view

import omok.model.Stone
import omok.model.board.Board
import omok.model.board.BoardPositionState
import omok.model.position.DefaultPosition
import omok.model.position.Position

class OmokView {
    fun start(board: Board) {
        println(MESSAGE_START_OMOK)
        show(board)
    }

    fun position(
        stone: Stone,
        boundary: Int,
        lastPosition: Position? = null,
        forbiddenPosition: Position? = null,
    ): Position {
        if (forbiddenPosition != null) println(MESSAGE_FORBIDDEN_POSITION)
        print(MESSAGE_TURN.format(stone.toUIModel()))
        if (lastPosition != null) print(MESSAGE_POSITION_LAST_STONE_PLACED.format(lastPosition.toUIModel(boundary)))
        print(MESSAGE_REQUEST_POSITION)
        return readln().trim().uppercase().toPosition(boundary)
    }

    fun show(board: Board) {
        val boardToList: List<List<BoardPositionState>> =
            List(board.sideLength.value) { column ->
                List(board.sideLength.value) { row ->
                    board.stateAt(row, column)
                }
            }

        val lastIndex = board.sideLength.value - 1
        val boundary = board.sideLength.value
        boardToList.forEachIndexed { columnIndex: Int, row: List<BoardPositionState> ->
            row.forEachIndexed { rowIndex: Int, state: BoardPositionState ->
                val wantToShow: String =
                    columnIndex(rowIndex, columnIndex, boundary) +
                        when (state) {
                            BoardPositionState.Empty -> {
                                when {
                                    rowIndex == 0 && columnIndex == 0 -> "┌"
                                    rowIndex == 0 && columnIndex == lastIndex -> "└"
                                    rowIndex == lastIndex && columnIndex == lastIndex -> "┘"
                                    rowIndex == lastIndex && columnIndex == 0 -> "┐"
                                    columnIndex == 0 -> "┬"
                                    columnIndex == lastIndex -> "┴"
                                    rowIndex == 0 -> "├"
                                    rowIndex == lastIndex -> "┤"
                                    else -> "┼"
                                }
                            }

                            BoardPositionState.Exist.Black -> "●"
                            BoardPositionState.Exist.White -> "○"
                        } + if (rowIndex == lastIndex) "" else "──"
                print(wantToShow)
            }
            println()
        }
        println(rowIndex(lastIndex))
    }

    private fun columnIndex(
        rowIndex: Int,
        columnIndex: Int,
        boundary: Int,
    ): String =
        if (rowIndex == 0) {
            val columnToString: String = (boundary - columnIndex).toString()
            if (columnToString.length == 1) {
                "  $columnToString "
            } else {
                " $columnToString "
            }
        } else {
            ""
        }

    private fun rowIndex(lastIndex: Int): String {
        val range = Char(65)..Char(65 + lastIndex)
        return range.joinToString(separator = "  ", prefix = "    ")
    }

    fun show(winner: Stone) {
        println(MESSAGE_WINNER.format(winner.toUIModel()))
    }

    private fun Stone.toUIModel(): String =
        when (this) {
            Stone.BLACK -> "흑"
            Stone.WHITE -> "백"
        }

    private fun Position.toUIModel(boundary: Int): String = Char('A'.code + row.value).toString() + (boundary - column.value)

    private fun String.toPosition(boundary: Int): Position {
        val row: Int = this.first { !it.isDigit() } - 'A'
        val column: Int = boundary - this.filter { it.isDigit() }.toInt()
        return DefaultPosition(row, column)
    }

    companion object {
        private const val MESSAGE_START_OMOK = "오목 게임을 시작합니다.\n"
        private const val MESSAGE_FORBIDDEN_POSITION = "해당 위치는 금수입니다."
        private const val MESSAGE_TURN = "%s의 차례입니다. "
        private const val MESSAGE_POSITION_LAST_STONE_PLACED = "(마지막 돌의 위치: %s)"
        private const val MESSAGE_REQUEST_POSITION = "\n위치를 입력하세요: "
        private const val MESSAGE_WINNER = "%s돌이 이겼습니다."
    }
}
