package woowacourse.omok.model.rule

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.OmokBoardConfig.DOWN
import woowacourse.omok.model.board.OmokBoardConfig.MAX_BLINK_COUNT
import woowacourse.omok.model.board.OmokBoardConfig.MAX_X
import woowacourse.omok.model.board.OmokBoardConfig.MAX_Y
import woowacourse.omok.model.board.OmokBoardConfig.MIN_X
import woowacourse.omok.model.board.OmokBoardConfig.MIN_Y
import woowacourse.omok.model.board.OmokBoardConfig.RIGHT
import woowacourse.omok.model.board.OmokBoardConfig.STILL
import woowacourse.omok.model.board.OmokBoardConfig.UP
import woowacourse.omok.model.board.OmokBoardConfig.ZERO
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState

abstract class OmokRule(
    private val currentStone: PositionState = PositionState.BLACK_POSITION,
    protected val opponentStone: PositionState = PositionState.WHITE_POSITION,
    position: Position,
    val omokBoard: OmokBoard,
) {
    protected val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
    protected val adaptedPoint = OmokAdapter.adaptOmokPoint(position)
    protected val directions =
        listOf(
            Pair(RIGHT, STILL),
            Pair(RIGHT, DOWN),
            Pair(STILL, DOWN),
            Pair(RIGHT, UP),
        )

    abstract fun validate(): Boolean

    protected fun search(direction: Pair<Int, Int>): Pair<Int, Int> {
        var (x, y) = adaptedPoint
        val (dx, dy) = direction
        var stone = ZERO
        var blink = ZERO
        var blinkCount = ZERO
        while (willExceedBounds(x, y, dx, dy).not()) {
            x += dx
            y += dy
            when (adaptedBoard[y][x]) {
                currentStone -> {
                    stone++
                    blink = blinkCount
                }

                opponentStone -> break
                PositionState.NONE -> {
                    if (blink == MAX_BLINK_COUNT) break
                    if (blinkCount++ == MAX_BLINK_COUNT) break
                }

                PositionState.FORBIDDEN -> break

                else -> throw IllegalArgumentException("스톤 케이스를 에러")
            }
        }
        return Pair(stone, blink)
    }

    protected fun countToWall(direction: Pair<Int, Int>): Int {
        var (x, y) = adaptedPoint
        val (dx, dy) = direction
        var distance = ZERO
        while (willExceedBounds(x, y, dx, dy).not()) {
            x += dx
            y += dy
            when (adaptedBoard[y][x]) {
                in listOf(currentStone, PositionState.NONE) -> distance++
                opponentStone -> break
                PositionState.FORBIDDEN -> break
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
            dx > ZERO && x == MAX_X -> true
            dx < ZERO && x == MIN_X -> true
            dy > ZERO && y == MAX_Y -> true
            dy < ZERO && y == MIN_Y -> true
            else -> false
        }
}
