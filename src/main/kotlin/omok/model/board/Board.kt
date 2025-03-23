package omok.model.board

import omok.model.rule.OmokRuleJudge

class Board(
    val points: BoardPoints,
    private val judge: OmokRuleJudge,
) {
    fun findPointState(point: Point): PointState? = points.getState(point)

    fun placeStone(
        point: Point,
        pointState: PointState,
    ): PlaceStoneResult {
        val state = findPointState(point) ?: return PlaceStoneResult.Failure.InvalidPoint
        if (state != PointState.OPEN) return PlaceStoneResult.Failure.AlreadyPlaced

        if (!judge.validate(this, point, pointState)) return PlaceStoneResult.Failure.Closed
        points.update(point, pointState)

        if (judge.isWin(this, point, pointState)) return PlaceStoneResult.Success.Placed(point)
        return PlaceStoneResult.Success.Finished(point)
    }
}
