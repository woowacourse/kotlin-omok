package omock.model.turn

sealed class ErrorType : Throwable() {
    class ThreeToThreeCount : Throwable()

    class FourToFourCount : Throwable()

    class IsReverseTwoAndThree : Throwable()

    class IsClearFourToFourCount : Throwable()
}
