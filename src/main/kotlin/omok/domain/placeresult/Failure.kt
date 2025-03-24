package omok.domain.placeresult

sealed class Failure : PlaceResult {
    data object AlreadyExistStone : Failure()

    data object InvalidPosition : Failure()
}
