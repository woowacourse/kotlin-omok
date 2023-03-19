package error

import domain.Stone

sealed class PlaceStoneResult : OmokError {
    data class Success(val stone: Stone) : PlaceStoneResult()
    object DuplicatedCoordinate : PlaceStoneResult()
    object ThreeToThree : PlaceStoneResult()
    object FourToFour : PlaceStoneResult()
    object LongPlaceStone : PlaceStoneResult()
}
