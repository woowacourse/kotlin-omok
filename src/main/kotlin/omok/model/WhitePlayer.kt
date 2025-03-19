package omok.model

import rule.OmokRule
import rule.WhiteRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class WhitePlayer(
    points: List<Point> = emptyList(),
) : Player(points) {
    override val rule: OmokRule = WhiteRenjuRule()

    override fun place(
        newPoint: Point,
        otherPoints: List<Point>,
    ) {
        TODO("Not yet implemented")
    }

    override fun checkViolation(
        newPoint: Point,
        otherPoints: List<Point>,
    ): Violation {
        TODO("Not yet implemented")
    }
}
