sealed class PlaceResult {
    sealed class Success : PlaceResult() {
        data object Progress : Success()

        data class Finish(
            val result: GameResult,
        ) : Success()
    }

    sealed class Failure : PlaceResult() {
        data object AlreadyExist : Failure()

        data object InvalidPosition : Failure()
    }
}
