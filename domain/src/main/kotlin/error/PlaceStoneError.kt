package error

sealed interface PlaceStoneError : OmokError {
    object DuplicatedCoordinate : PlaceStoneError {
        override val message: String
            get() = "중복된 위치에 이미 돌이 있습니다."
    }

    object ThreeToThree : PlaceStoneError {
        override val message: String
            get() = "3-3입니다."
    }

    object FourToFour : PlaceStoneError {
        override val message: String
            get() = "4-4입니다."

    }
    object LongPlaceStone : PlaceStoneError {
        override val message: String
            get() = "육목입니다."

    }
}
