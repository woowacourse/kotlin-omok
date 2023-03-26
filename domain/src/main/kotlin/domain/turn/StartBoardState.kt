package domain.turn

import domain.judgement.Rule
import domain.stone.Position
import domain.stone.Stone

class StartBoardState(
    private val rule: Rule
) : BoardState(Board(POSITIONS.associateWith { null }.toMap()), null) {

    override fun putStone(stone: Stone): BoardState {
        val nextBoard = board.putStone(stone)
        return RunningBoardState(rule, nextBoard, stone)
    }

    override fun isFinished(): Boolean = false

    companion object {
        private val POSITIONS: List<Position> = Position.all()
    }
}
