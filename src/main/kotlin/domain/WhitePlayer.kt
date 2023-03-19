package domain

import error.PlaceStoneResult

class WhitePlayer(override val color: Color = Color.WHITE) : Player() {
    override fun validateOmokRule(stone: Stone, omokRule: OmokRule): PlaceStoneResult =
        PlaceStoneResult.Success(stone)
}
