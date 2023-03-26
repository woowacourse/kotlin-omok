package domain.turn

import domain.judgement.Rule
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class StartBoardState(
    private val rule: Rule
) : BoardState(Board(POSITIONS.associateWith { null }.toMap()), null) {

    override fun putStone(stone: Stone): BoardState {
        require(stone.color == Color.BLACK) { ERROR_IS_NOT_START_BLACK }
        val nextBoard = board.putStone(stone)
        return RunningBoardState(rule, nextBoard, stone)
    }

    override fun isFinished(): Boolean = false

    companion object {
        private val POSITIONS: List<Position> = Position.all()
        private const val ERROR_IS_NOT_START_BLACK = "[ERROR] 첫 돌은 반드시 검은 색을 놔야 합니다"
    }
}
