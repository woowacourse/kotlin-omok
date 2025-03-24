package omok.model.board

import omok.model.rule.OmokRuleJudge

class Board(
    val points: BoardPoints,
    private val judge: OmokRuleJudge,
) {
    fun findStoneColor(point: Point): StoneColor? = points.getState(point)

    fun placeStone(
        point: Point,
        color: StoneColor,
    ): PlaceStoneResult {
        if (!checkOutOfBounds(point)) return PlaceStoneResult.Failure.InvalidPoint
        if (checkAlreadyPlaced(point)) return PlaceStoneResult.Failure.AlreadyPlaced

        if (!judge.validate(this, point, color)) return PlaceStoneResult.Failure.Closed
        points.update(point, color)

        if (judge.isWin(this, point, color)) return PlaceStoneResult.Success.Placed(point)
        return PlaceStoneResult.Success.Finished(point)
    }

    private fun checkOutOfBounds(point: Point): Boolean {
        return listOf(point.x, point.y).all { it in BoardSize.MIN_SIZE..points.size.value }
    }

    private fun checkAlreadyPlaced(point: Point): Boolean {
        val color = findStoneColor(point)
        return color != null
    }
}
