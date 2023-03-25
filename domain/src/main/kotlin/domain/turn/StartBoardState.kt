package domain.turn

import domain.judgement.Rule
import domain.stone.Position
import domain.stone.Stone

class StartBoardState(
    private val rule: Rule
) : BoardState(POSITIONS.associateWith { null }.toMap(), null) {

    companion object {
        private val POSITIONS: List<Position> = Position.all()
    }

    override fun putStone(stone: Stone): BoardState {
        val nextBoard = board.toMutableMap().apply { this[stone.position] = stone.color }
        return RunningBoardState(rule, nextBoard, stone)
    }

    override fun isFinished(): Boolean = false
}
