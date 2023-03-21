package error

sealed interface PlaceStoneError : OmokError {
    object DuplicatedCoordinate : PlaceStoneError
    object ThreeToThree : PlaceStoneError
    object FourToFour : PlaceStoneError
    object LongPlaceStone : PlaceStoneError
}
