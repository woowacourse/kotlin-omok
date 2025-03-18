sealed class PlaceResult {
    data object Success : PlaceResult()

    sealed class Failure : PlaceResult() {
        data object AlreadyExist : Failure()

        data object InvalidPosition : Failure()
    }
}
