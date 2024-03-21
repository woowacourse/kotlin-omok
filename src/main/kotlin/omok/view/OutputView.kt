package omok.view

private const val COLUMN_STRING = "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"
private const val X = "X"
private const val EMPTY = "┼"
private const val BLACK_STONE = "●"
private const val WHITE_STONE = "○"

object OutputView {
    fun printStartMessage() {
        println("오목 게임을 시작합니다.")
    }

    fun printForbiddenMoveMessage() {
        println("놓을 수 없는 자리입니다.")
    }

    fun printOccupiedPositionMessage() {
        println("빈 자리에 놓아주세요.")
    }

    fun printWrongPositionMessage() {
        println("잘못된 위치 입력입니다.")
    }

    fun printBoard(
        board: Array<Array<Int>>,
        forbiddenPositions: List<Pair<Int, Int>> = emptyList(),
    ) {
        val boardForDisplay = initializeBoard()
        for (row in board.indices) {
            print("${row + 1}".padStart(2, ' ') + " ")
            for (col in board[row].indices) {
                val displayChar =
                    when {
                        row to col in forbiddenPositions -> X
                        board[col][row] == 1 -> WHITE_STONE
                        board[col][row] == -1 -> BLACK_STONE
                        else -> boardForDisplay[row][col]
                    }
                print(displayChar)
                if (col < board[row].lastIndex) print("──")
            }
            println()
        }
        println(COLUMN_STRING)
    }

    private fun initializeBoard(): Array<Array<String>> {
        return Array(15) { row ->
            Array(15) { col ->
                when {
                    row == 0 -> topEdge(col)
                    row == 14 -> bottomEdge(col)
                    col == 0 -> leftEdge(row)
                    col == 14 -> rightEdge(row)
                    else -> EMPTY
                }
            }
        }
    }

    private fun topEdge(col: Int) =
        when (col) {
            0 -> "┌"
            14 -> "┐"
            else -> "┬"
        }

    private fun bottomEdge(col: Int) =
        when (col) {
            0 -> "└"
            14 -> "┘"
            else -> "┴"
        }

    private fun leftEdge(row: Int) = if (row in 1..13) "├" else "┼"

    private fun rightEdge(row: Int) = if (row in 1..13) "┤" else "┼"
}
