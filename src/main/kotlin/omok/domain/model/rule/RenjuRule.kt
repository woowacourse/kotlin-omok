package omok.domain.model.rule

import omok.domain.model.Board
import omok.domain.model.position.OmokStone
import omok.domain.model.stone.StoneType
import rule.wrapper.point.Point

class RenjuRule(private val omokRule: rule.OmokRule) : OmokRule {
    override fun canPlace(
        omokStone: OmokStone,
        board: Board,
    ): Boolean {
        val blackPoints = board.getStones(StoneType.BLACK).map { it.toPoint() }
        val whitePoints = board.getStones(StoneType.WHITE).map { it.toPoint() }
        val startPoint = omokStone.toPoint()
        return omokRule.checkAnyFoulCondition(blackPoints, whitePoints, startPoint).state.not()
    }

    private fun OmokStone.toPoint() = Point(this.position.row.value, this.position.column.value)
}
