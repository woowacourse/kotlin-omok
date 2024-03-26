package omock.model

sealed class ErrorType : Throwable() {
    class ThreeToThreeCount : Throwable()

    class FourToFourCount : Throwable()

    class IsReverseTwoAndThree : Throwable()

    class IsClearFourToFourCount : Throwable()

    class AlreadyExistStone : Throwable()
}
