package woowacourse.omok.model.rule.lib

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.StoneColor
import woowacourse.omok.model.rule.GameRule

class ForbiddenMoveRuleAdapter(
    private val forbiddenMoveRule: ForbiddenMoveRule,
) : GameRule {
    override fun validateMove(
        board: Board,
        validationPoint: Point,
    ): Boolean {
        val position = converteOmokPoint(validationPoint)
        return forbiddenMoveRule.validate(board.toList(), position)
    }

    private fun Board.toList(): List<List<Int>> {
        val adapted = MutableList(this.size) { MutableList(this.size) { ForbiddenMoveRule.EMPTY_STONE } }
        this.points.forEach { point ->
            adapted[point.key.y - 1][point.key.x - 1] = point.value.toInt()
        }

        return adapted
    }

    private fun StoneColor.toInt(): Int =
        when (this) {
            StoneColor.BLACK -> ForbiddenMoveRule.BLACK_STONE
            StoneColor.WHITE -> ForbiddenMoveRule.WHITE_STONE
            else -> ForbiddenMoveRule.EMPTY_STONE
        }

    private fun converteOmokPoint(point: Point): Pair<Int, Int> = Pair(point.x - 1, point.y - 1)
}
