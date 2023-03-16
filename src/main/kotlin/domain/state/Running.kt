package domain.state

import domain.rule.OmokRule
import domain.stone.Board
import domain.stone.Stone
import domain.stone.StoneType

abstract class Running(val board: Board) : State {
    abstract override fun put(stone: Stone): State

    override fun getWinner(): StoneType {
        TODO("Not yet implemented")
    }

    override fun isValidPut(stone: Stone): Boolean {
        return !board.stones.containsPosition(stone)
    }

    override fun isOmokCondition(stone: Stone): Boolean {
        return OmokRule.isOmokCondition(board.board, stone)
    }
}
