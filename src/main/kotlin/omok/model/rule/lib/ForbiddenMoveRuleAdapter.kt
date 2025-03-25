package omok.model.rule.lib

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.StoneColor
import omok.model.rule.GameRule

class ForbiddenMoveRuleAdapter(
    private val forbiddenMoveRule: ForbiddenMoveRule,
) : GameRule {
    override fun validateMove(
        board: Board,
        previousPoint: Point,
    ): Boolean {
        val position = converteOmokPoint(previousPoint)
        return forbiddenMoveRule.validate(board.toList(), position)
    }

    private fun Board.toList(): List<List<Int>> {
        val adapted = MutableList(this.size) { MutableList(this.size) { ForbiddenMoveRule.EMPTY_STONE } }
        this.points.forEach { point ->
            adapted[point.key.y - 1][point.key.x - 1] = point.value.toInt()
        }

        return adapted
    }

    private fun StoneColor?.toInt(): Int {
        return when (this) {
            StoneColor.BLACK -> ForbiddenMoveRule.BLACK_STONE
            StoneColor.WHITE -> ForbiddenMoveRule.WHITE_STONE
            else -> ForbiddenMoveRule.EMPTY_STONE
        }
    }

    fun converteOmokPoint(point: Point): Pair<Int, Int> {
        return Pair(point.x - 1, point.y - 1)
    }
}
