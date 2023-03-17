package domain.board

import domain.OmokResult
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class PlayingBoard(placedStones: List<Stone> = listOf()) : BasedBoard(placedStones.toList()) {
    constructor(vararg stone: Stone) : this(stone.toList())

    override val winningColor: Color
        get() {
            throw IllegalStateException(PLAYING_GAME_ERROR)
        }

    override val isFinished: Boolean = false

    override fun isPossiblePut(position: Position): Boolean =
        !placedStones.any { stone -> stone.position == position }

    override fun putStone(stone: Stone): Board {
        if (isPossiblePut(stone.position).not()) return this
        return nextBoard(stone)
    }

    private fun nextBoard(newStone: Stone): Board {
        val omokResult = OmokResult.valueOf(getStones(), newStone)
        val nextStones = getStones() + newStone
        return when (omokResult) {
            OmokResult.FIVE_STONE_WINNING -> FinishedBoard(nextStones, newStone.color)
            OmokResult.FORBIDDEN -> FinishedBoard(nextStones, Color.WHITE)
            OmokResult.RUNNING -> PlayingBoard(nextStones)
        }
    }

    companion object {
        private const val PLAYING_GAME_ERROR = "[ERROR] 현재 게임이 진행중입니다."
    }
}
