package woowacourse.omok.model.rule

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.OmokBoardConfig.BLACK_STONE
import woowacourse.omok.model.board.OmokBoardConfig.EMPTY_STONE
import woowacourse.omok.model.board.OmokBoardConfig.MAX_X
import woowacourse.omok.model.board.OmokBoardConfig.MAX_Y
import woowacourse.omok.model.board.OmokBoardConfig.MIN_X
import woowacourse.omok.model.board.OmokBoardConfig.MIN_Y
import woowacourse.omok.model.board.OmokBoardConfig.WHITE_STONE
import woowacourse.omok.model.board.Position

abstract class OmokRule(
    private val currentStone: Int = BLACK_STONE,
    protected val opponentStone: Int = WHITE_STONE,
    position: Position,
    omokBoard: OmokBoard,
) {
    protected val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
    protected val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
    protected val directions = listOf(Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(1, -1))

    abstract fun validate(): Boolean

    protected fun search(direction: Pair<Int, Int>): Pair<Int, Int> {
        var (x, y) = adaptedPoint
        val (dx, dy) = direction
        var stone = 0
        var blink = 0
        var blinkCount = 0
        while (willExceedBounds(x, y, dx, dy).not()) {
            x += dx
            y += dy
            when (adaptedBoard[y][x]) {
                currentStone -> {
                    stone++
                    blink = blinkCount
                }

                opponentStone -> break
                EMPTY_STONE -> {
                    if (blink == 1) break
                    if (blinkCount++ == 1) break
                }

                else -> throw IllegalArgumentException("스톤 케이스를 에러")
            }
        }
        return Pair(stone, blink)
    }

    protected fun countToWall(direction: Pair<Int, Int>): Int {
        var (x, y) = adaptedPoint
        val (dx, dy) = direction
        var distance = 0
        while (willExceedBounds(x, y, dx, dy).not()) {
            x += dx
            y += dy
            when (adaptedBoard[y][x]) {
                in listOf(currentStone, EMPTY_STONE) -> distance++
                opponentStone -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }

    private fun willExceedBounds(
        x: Int,
        y: Int,
        dx: Int,
        dy: Int,
    ): Boolean =
        when {
            dx > 0 && x == MAX_X -> true
            dx < 0 && x == MIN_X -> true
            dy > 0 && y == MAX_Y -> true
            dy < 0 && y == MIN_Y -> true
            else -> false
        }
}
