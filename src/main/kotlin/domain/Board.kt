package domain

import error.ErrorHandler
import error.PlaceStoneResult

class Board(private val players: Players, private val stones: Stones) {
    fun repeatTurn(coordinateGenerator: CoordinateGenerator, omokRule: OmokRule, errorHandler: ErrorHandler): Player {
        var currentTurnPlayer = players.nextPlayer
        while (true) {
            when (val result = stones.makeValidatedStone(currentTurnPlayer, coordinateGenerator, omokRule)) {
                is PlaceStoneResult.Success -> {
                    val stone = result.stone
                    stones.place(stone)
                    if (isWinPlace(stone, omokRule)) return currentTurnPlayer
                    currentTurnPlayer = players.nextPlayer
                }
                else ->
                    errorHandler.log(result)
            }
        }
    }

    private fun isWinPlace(stone: Stone, omokRule: OmokRule): Boolean = omokRule.findScore(stone) >= WINNING_CONDITION

    companion object {
        val BOARD_SIZE = Vector(15, 15)
        val BOARD_START_Vector = Vector(0, 0)
        private const val WINNING_CONDITION = 4
    }
}
