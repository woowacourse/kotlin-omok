package woowacourse.omok.model.rule

sealed class PlacementError {
    data object AlreadyOccupiedViolation : PlacementError()

    data object DoubleThreeViolation : PlacementError()

    data object DoubleFourViolation : PlacementError()

    data object OverlineViolation : PlacementError()

    data object NoViolation : PlacementError()
}
