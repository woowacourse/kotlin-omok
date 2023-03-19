package domain

import error.PlaceStoneResult

class BlackPlayer(override val color: Color = Color.BLACK) : Player() {
    override fun validateOmokRule(stone: Stone, omokRule: OmokRule): PlaceStoneResult {
        if (omokRule.isThreeToThree(stone))
            return PlaceStoneResult.ThreeToThree
        if (omokRule.isFourToFour(stone))
            return PlaceStoneResult.FourToFour
        if (omokRule.findScore(stone) >= LARGE_PLACE)
            return PlaceStoneResult.LongPlaceStone
        return PlaceStoneResult.Success(stone)
    }

    companion object {
        const val LARGE_PLACE = 5
    }
}
