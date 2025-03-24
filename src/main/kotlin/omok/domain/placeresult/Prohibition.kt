package omok.domain.placeresult

sealed class Prohibition : PlaceResult {
    data object DoubleThreeViolation : Prohibition()

    data object DoubleFourViolation : Prohibition()

    data object OverlineViolation : Prohibition()
}
