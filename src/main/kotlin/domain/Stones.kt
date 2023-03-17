package domain

class Stones(value: List<Stone> = listOf()) {
    private val _value: MutableList<Stone> = value.toMutableList()
    val value: List<Stone>
        get() = _value.toList()

    fun place(stone: Stone) {
        _value.add(stone)
    }

    fun makeValidatedStone(player: Player, coordinateReader: CoordinateReader): Stone {
        val coordinate = coordinateReader.read(player.color)
        val stone = Stone(player.color, coordinate)
        if (!player.validateOmokRule(this, stone) || !validateDuplicatedCoordinate(stone)) {
            return makeValidatedStone(player, coordinateReader)
        }
        return stone
    }

    private fun validateDuplicatedCoordinate(stone: Stone): Boolean {
        return value.none { it.coordinate == stone.coordinate }
    }

    fun isWinPlace(stone: Stone): Boolean {
        return RenjuRule.findScore(this, stone) >= WINNING_CONDITION
    }

    companion object {
        private const val MESSAGE_CORRUPT_STONE = "같은 위치에 이미 돌이 있습니다. 위치 : (%d, %d)"
        private const val WINNING_CONDITION = 4
    }
}
