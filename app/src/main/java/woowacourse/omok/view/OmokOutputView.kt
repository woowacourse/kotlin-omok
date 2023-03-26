package woowacourse.omok.view

import domain.game.Omok.Companion.OMOK_BOARD_SIZE
import domain.point.Point
import domain.stone.StoneColor
import woowacourse.omok.view.mapper.toPresentation

class OmokOutputView : OutputView {
    override fun startGame() {
        println("오목 게임을 시작합니다.\n")
        println(OMOK_BOARD)
    }

    override fun drawStone(lastStoneColor: StoneColor, newPoint: Point) {
        val pointIndex = calculateIndex(newPoint, OMOK_BOARD_SIZE)
        val frontBoard = OMOK_BOARD.substring(0, pointIndex)
        val backBoard = OMOK_BOARD.substring(pointIndex + 1)

        OMOK_BOARD = when (lastStoneColor) {
            StoneColor.BLACK -> frontBoard + BLACK_STONE + backBoard
            StoneColor.WHITE -> frontBoard + WHITE_STONE + backBoard
        }
        println(OMOK_BOARD)
    }

    override fun showPutFailed() {
        println("그 자리에는 놓을 수 없습니다!")
    }

    override fun showResult(lastStoneColor: StoneColor, winnerStoneColor: StoneColor, newPoint: Point) {
        drawStone(lastStoneColor, newPoint)
        println(GAME_END_MESSAGE)
        println(WINNER_MESSAGE.format(winnerStoneColor.toPresentation().text))
    }

    override fun showThisTurn(stoneColor: StoneColor, point: Point?) {
        print("\n${stoneColor.toPresentation().text}의 차례입니다.\n")
        if (point != null) {
            val viewPosition = point.toPresentation()
            print("(마지막 돌의 위치: $viewPosition)\n")
        }
    }

    private fun calculateIndex(point: Point, size: Int): Int =
        (1 + 3 * point.col) + (size - point.row) * (3 + 3 * size)

    companion object {
        private const val GAME_END_MESSAGE = "\n게임이 종료되었습니다."
        private const val WINNER_MESSAGE = "%s의 승리입니다."
        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '○'
        private const val BOTTOM_NUMBER = 1

        private fun createOmokBoard(size: Int): String {
            val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            val topHorizontal = "┌──" + "┬──".repeat(size - 3) + "┬──┐"
            val bottomHorizontal = "└──" + "┴──".repeat(size - 3) + "┴──┘"
            val middleHorizontal = "├──" + "┼──".repeat(size - 3) + "┼──┤"
            val board = StringBuilder()
            for (i in size downTo 1) {
                board.append(String.format("%3d ", i))
                when {
                    i == size -> board.append(topHorizontal) // top
                    i == BOTTOM_NUMBER -> board.append(bottomHorizontal) // middle
                    i != BOTTOM_NUMBER -> board.append(middleHorizontal) // bottom
                }
                board.append("\n")
            }
            board.append("    ")
            for (j in 0 until size) { board.append(letters[j] + "  ") }
            return board.toString()
        }
        private var OMOK_BOARD: String = createOmokBoard(OMOK_BOARD_SIZE)
    }
}
