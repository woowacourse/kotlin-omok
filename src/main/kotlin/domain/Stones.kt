package domain

import error.PlaceStoneResult

class Stones(value: List<Stone> = listOf()) {
    private val _value: MutableList<Stone> = value.toMutableList()
    val value: List<Stone>
        get() = _value.toList()

    fun place(stone: Stone) {
        _value.add(stone)
    }

    fun makeValidatedStone(
        player: Player,
        coordinateGenerator: CoordinateGenerator,
        omokRule: OmokRule
    ): PlaceStoneResult {
        val coordinate = coordinateGenerator.read(player.color)
        val stone = Stone(player.color, coordinate)
        val validateOmokRuleResult = validateOmokRule(player.color, stone, omokRule)
        if (validateOmokRuleResult !is PlaceStoneResult.Success) {
            return validateOmokRuleResult
        }
        if (!validateDuplicatedCoordinate(stone)) {
            return PlaceStoneResult.DuplicatedCoordinate
        }
        return PlaceStoneResult.Success(stone)
    }

    private fun validateOmokRule(playerColor: Color, stone: Stone, omokRule: OmokRule): PlaceStoneResult {
        if (playerColor == Color.WHITE)
            return PlaceStoneResult.Success(stone)
        if (omokRule.isThreeToThree(stone))
            return PlaceStoneResult.ThreeToThree
        if (omokRule.isFourToFour(stone))
            return PlaceStoneResult.FourToFour
        if (omokRule.findScore(stone) >= LARGE_PLACE)
            return PlaceStoneResult.LongPlaceStone
        return PlaceStoneResult.Success(stone)
    }

    fun validateDuplicatedCoordinate(stone: Stone): Boolean {
        return value.none { it.coordinate == stone.coordinate }
    }

    companion object {
        const val LARGE_PLACE = 5
    }
}
