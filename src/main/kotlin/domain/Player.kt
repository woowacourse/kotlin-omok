package domain

import error.PlaceStoneResult

sealed class Player {
    abstract val color: Color
    abstract fun validateOmokRule(stone: Stone, omokRule: OmokRule): PlaceStoneResult
}
