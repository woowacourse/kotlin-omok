package domain

class Board(private val players: Players, private val stones: Stones, private val omokRule: OmokRule) {
    fun repeatTurn(coordinateReader: CoordinateReader): Player {
        while (true) {
            val currentTurnPlayer = players.currentTurn()
            val stone = makeValidatedStone(currentTurnPlayer, coordinateReader)
            stones.place(stone)
            if (isWinPlace(stone)) return currentTurnPlayer
        }
    }

    private fun makeValidatedStone(player: Player, coordinateReader: CoordinateReader): Stone {
        val coordinate = coordinateReader.read(player.color)
        val stone = Stone(player.color, coordinate)
        if (!player.validateOmokRule(stone, omokRule) || !stones.validateDuplicatedCoordinate(stone)) {
            return makeValidatedStone(player, coordinateReader)
        }
        return stone
    }

    private fun isWinPlace(stone: Stone): Boolean = omokRule.findScore(stone) >= WINNING_CONDITION

    companion object {
        val BOARD_SIZE = Point(15, 15)
        val BOARD_START_POINT = Point(0, 0)
        private const val WINNING_CONDITION = 4
    }
}
