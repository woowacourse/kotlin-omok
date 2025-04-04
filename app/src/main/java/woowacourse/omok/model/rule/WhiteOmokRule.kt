package woowacourse.omok.model.rule

import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardDimensions
import woowacourse.omok.model.rule.PlacementError.NoViolation
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class WhiteOmokRule(
    private val dimensions: BoardDimensions,
) : OmokRule {
    override fun isWin(
        board: Board,
        lastStone: Stone,
    ): Boolean = calculateTotalCount(board.stonesMap, lastStone) >= OMOK_DEFAULT_VALUE

    override fun validate(
        board: Board,
        nextPosition: Position,
        color: StoneColor,
    ): PlacementError = NoViolation

    private fun calculateTotalCount(
        stonesMap: Map<Position, StoneColor>,
        lastStone: Stone,
    ): Int =
        Direction.lineDirections().maxOf { (directionA, directionB) ->
            val countA = countConnected(stonesMap, lastStone, directionA)
            val countB = countConnected(stonesMap, lastStone, directionB)

            countA + countB + 1
        }

    private fun countConnected(
        stonesMap: Map<Position, StoneColor>,
        stone: Stone,
        direction: Direction,
    ): Int {
        var count = 0
        val stonePosition = stone.position
        var x = stonePosition.row.value + direction.dx
        var y = stonePosition.col.value + direction.dy

        while (x in 0 until dimensions.width && y in 0 until dimensions.height) {
            val nextPos = Position(Row(x), Col(y))
            if (stonesMap[nextPos] == stone.stoneColor) {
                count++
                x += direction.dx
                y += direction.dy
            } else {
                break
            }
        }
        return count
    }

    companion object {
        private const val OMOK_DEFAULT_VALUE = 5
    }
}
