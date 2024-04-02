package woowacourse.omok.domain.omok.model.state

import woowacourse.omok.domain.omok.mapper.toArkOmokBoard
import woowacourse.omok.domain.omok.mapper.toArkOmokPoint
import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.Color
import woowacourse.omok.domain.omok.model.Position
import woowacourse.omok.domain.omok.strategy.ArkForbiddenPlaceJudge
import woowacourse.omok.domain.omok.strategy.ForbiddenPlaceJudge

class Black(
    private val blackStatus: Array<Array<Color>>,
    private val forbiddenPlaceJudge: ForbiddenPlaceJudge = ArkForbiddenPlaceJudge(),
) : TurnState() {
    override fun addStone(
        position: Position,
        placeStone: (Position) -> Unit,
    ) {
        val arkBoard = blackStatus.toArkOmokBoard()
        val row = Board.ARRAY_SIZE - position.row.value
        val col = position.col.title
        val arkPoint = Position.of(row, col).toArkOmokPoint()
        if (isNotForbiddenPlace(arkBoard, arkPoint)) {
            placeStone(position)
        } else {
            throw IllegalArgumentException(EXCEPTION_FORBIDDEN_PLACEMENT)
        }
    }

    private fun isNotForbiddenPlace(
        board: List<List<Int>>,
        point: Pair<Int, Int>,
    ) = forbiddenPlaceJudge.isNotForbiddenPlace(board, point)

    companion object {
        private const val EXCEPTION_FORBIDDEN_PLACEMENT = "금수인 위치입니다."
    }
}
