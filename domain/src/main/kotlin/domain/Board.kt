package domain

import domain.omokrule.OmokRule
import error.OmokResult
import error.PlaceStoneError

class Board(private val players: Players = Players(), val stones: Stones = Stones()) {
    var currentPlayer: Player = Player.BlackPlayer()
    fun repeatTurn(
        coordinate: Coordinate,
        omokRule: OmokRule
    ): PlaceStoneError {
        return when (
            val result =
                stones.makeValidatedStone(currentPlayer, coordinate, omokRule)
        ) {
            is OmokResult.Success<*> -> {
                val stone = result.value as Stone
                stones.place(stone)
                currentPlayer = players.nextPlayer(currentPlayer)
                result
            }
            else -> result
        }
    }

    fun isWinPlace(stone: Stone, omokRule: OmokRule): Boolean =
        omokRule.findScore(stone) >= WINNING_CONDITION

    companion object {
        val BOARD_SIZE = Vector(15, 15)
        val BOARD_START_Vector = Vector(0, 0)
        private const val WINNING_CONDITION = 4
    }
}
