package omok.model

import omok.model.adapter.RenjuRuleAdapter
import omok.model.game.FoulCondition
import omok.model.game.GameState
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.Stones

class Board(
    val stones: Stones = Stones(),
    private val renjuRuleAdapter: RenjuRuleAdapter,
) {
    fun place(newStone: Stone) {
        when (foulCondition(stones, newStone)) {
            FoulCondition.DOUBLE_THREE -> TODO()
            FoulCondition.DOUBLE_FOUR -> TODO()
            FoulCondition.OVERLINE -> TODO()
            FoulCondition.NONE -> {
                stones.add(newStone)
                stones.setLastStone(newStone)
            }
        }
    }

    fun gameState(newStone: Stone): GameState {
        if (!renjuRuleAdapter.checkWin(stones.stones, newStone)) {
            return GameState.PLAYING
        }
        return when (newStone.color) {
            StoneColor.BLACK -> GameState.BLACK_OMOK
            StoneColor.WHITE -> GameState.WHITE_OMOK
        }
    }

    fun foulCondition(
        stones: Stones,
        newStone: Stone,
    ): FoulCondition = renjuRuleAdapter.checkAnyFoulCondition(stones.stones, newStone)

    companion object {
        const val MAX_BOARD_HEIGHT = 15
        const val MAX_BOARD_WIDTH = 15
    }
}
