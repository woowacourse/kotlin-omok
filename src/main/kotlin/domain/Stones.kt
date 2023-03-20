package domain

import error.OmokResult
import error.PlaceStoneError

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
    ): PlaceStoneError {
        val coordinate = coordinateGenerator.read(player.color)
        val stone = Stone(player.color, coordinate)
        val validateOmokRuleResult = validateOmokRule(player.color, stone, omokRule)
        if (validateOmokRuleResult !is OmokResult.Success<*>) {
            return validateOmokRuleResult
        }
        if (!validateDuplicatedCoordinate(stone)) {
            return PlaceStoneError.DuplicatedCoordinate
        }
        return OmokResult.Success(stone)
    }

    private fun validateOmokRule(playerColor: Color, stone: Stone, omokRule: OmokRule): PlaceStoneError {
        if (playerColor is Color.White)
            return OmokResult.Success(stone)
        if (omokRule.isThreeToThree(stone))
            return PlaceStoneError.ThreeToThree
        if (omokRule.isFourToFour(stone))
            return PlaceStoneError.FourToFour
        if (omokRule.findScore(stone) >= LARGE_PLACE)
            return PlaceStoneError.LongPlaceStone
        return OmokResult.Success(stone)
    }

    fun validateDuplicatedCoordinate(stone: Stone): Boolean {
        return value.none { it.coordinate == stone.coordinate }
    }

    companion object {
        const val LARGE_PLACE = 5
    }
}
