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
        val validateOmokRuleResult = player.validateOmokRule(stone, omokRule)
        if (validateOmokRuleResult !is PlaceStoneResult.Success) {
            return validateOmokRuleResult
        }
        if (!validateDuplicatedCoordinate(stone)) {
            return PlaceStoneResult.DuplicatedCoordinate
        }
        return PlaceStoneResult.Success(stone)
    }

    fun validateDuplicatedCoordinate(stone: Stone): Boolean {
        return value.none { it.coordinate == stone.coordinate }
    }
}
