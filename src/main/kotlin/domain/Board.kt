package domain

class Board(private val players: Players, private val stones: Stones, private val omokRule: OmokRule) {
    fun repeatTurn(coordinateGenerator: CoordinateGenerator): Player {
        while (true) {
            val currentTurnPlayer = players.currentPlayer
            val stone = makeValidatedStone(currentTurnPlayer, coordinateGenerator)
            stones.place(stone)
            if (isWinPlace(stone)) return currentTurnPlayer
        }
    }

    private fun makeValidatedStone(player: Player, coordinateGenerator: CoordinateGenerator): Stone {
        val coordinate = coordinateGenerator.read(player.color)
        val stone = Stone(player.color, coordinate)
        if (!player.validateOmokRule(stone, omokRule) || !stones.validateDuplicatedCoordinate(stone)) {
            return makeValidatedStone(player, coordinateGenerator)
        }
        return stone
    }

    private fun isWinPlace(stone: Stone): Boolean = omokRule.findScore(stone) >= WINNING_CONDITION

    companion object {
        val BOARD_SIZE = Vector(15, 15)
        val BOARD_START_Vector = Vector(0, 0)
        private const val WINNING_CONDITION = 4
    }
}
