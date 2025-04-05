import woowacourse.omok.domain.Point
import woowacourse.omok.domain.state.PlaceResult
import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.StoneColor

class OutputView {
    fun printStartOmok(boardSize: Int) {
        println(MESSAGE_START_OMOK)
        println(createOmokBoard(boardSize))
    }

    fun printTurn(
        color: StoneColor,
        lastPoint: Point?,
    ) {
        print(MESSAGE_TURN.format(color.toText()))
        if (lastPoint != null) {
            print(MESSAGE_LAST_POINT.format(lastPoint.toText()))
        }
    }

    fun printOmokBoard(
        stones: OmokStones,
        boardSize: Int,
    ) {
        val board = StringBuilder(createOmokBoard(boardSize))
        stones.stones.forEach { stone ->
            when (stone.color) {
                StoneColor.BLACK ->
                    board.setCharAt(calculatePosition(stone.point, boardSize), BLACK_STONE)

                StoneColor.WHITE ->
                    board.setCharAt(calculatePosition(stone.point, boardSize), WHITE_STONE)
            }
        }
        println(board)
    }

    fun printForbiddenMove(forbiddenMove: PlaceResult.ForbiddenMove) {
        println(
            when (forbiddenMove) {
                PlaceResult.ForbiddenMove.DoubleThree -> "3–3 위치에 놓을 수 없습니다."
                PlaceResult.ForbiddenMove.DoubleFour -> "4–4 위치에 놓을 수 없습니다."
                PlaceResult.ForbiddenMove.Overline -> "장목 위치에 놓을 수 없습니다."
                PlaceResult.ForbiddenMove.Occupied -> "이미 돌이 놓여져 있습니다."
                PlaceResult.ForbiddenMove.OutOfBoard -> "오목판의 범위를 넘어간 좌표입니다."
            },
        )
    }

    fun printWinner(color: StoneColor?) {
        if (color != null) {
            println(MESSAGE_WINNER.format(color.toText()))
        } else {
            println(MESSAGE_DRAW)
        }
    }

    private fun calculatePosition(
        point: Point,
        boardSize: Int,
    ): Int = (point.row + 1) * 3 + ((boardSize * 3 + 2) * (boardSize - point.col - 1))

    private fun createOmokBoard(boardSize: Int): String {
        buildString {
            append(String.format("%2d", boardSize))
            append(" ┌")
            append("──┬".repeat(boardSize - 2)).append("──┐\n")

            for (y in boardSize - 2 downTo 1) {
                append(String.format("%2d", y + 1))
                append(" ├")
                append("──┼".repeat(boardSize - 2)).append("──┤\n")
            }

            append(" 1")
            append(" └")
            append("──┴".repeat(boardSize - 2)).append("──┘\n")

            append("   ")
            for (c in 0..<boardSize - 1) {
                append(('A' + c)).append("  ")
            }
            append('A' + boardSize - 1)
            return toString()
        }
    }

    companion object {
        private const val MESSAGE_START_OMOK = "오목 게임을 시작합니다."
        private const val MESSAGE_TURN = "\n%s의 차례입니다."
        private const val MESSAGE_LAST_POINT = " (마지막 돌의 위치: %s)"
        private const val MESSAGE_WINNER = "%s이 승리했습니다."
        private const val MESSAGE_DRAW = "더 이상 돌을 놓을 곳이 없습니다. 게임을 종료합니다."

        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '○'

        private fun StoneColor.toText(): String =
            when (this) {
                StoneColor.BLACK -> "흑"
                StoneColor.WHITE -> "백"
            }

        private fun Point.toText(): String = ('A' + row).toString() + (col + 1)
    }
}
