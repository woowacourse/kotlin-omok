package domain.domain.state

sealed class Foul : State {
    data object Duplicated : Foul()

    data object DoubleThree : Foul()

    data object DoubleFour : Foul()

    data object OverLine : Foul()
}
