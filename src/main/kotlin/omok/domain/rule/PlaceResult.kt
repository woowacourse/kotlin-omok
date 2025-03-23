package omok.domain.rule

import omok.domain.player.PlayerStone

sealed class PlaceResult {
    sealed class Success : PlaceResult() {
        data class Progress(
            val lastStone: PlayerStone,
        ) : Success()

        data class Finish(
            val gameResult: GameResult,
        ) : Success()
    }

    sealed class Failure : PlaceResult() {
        data object AlreadyExistStone : Failure()

        data object InvalidPosition : Failure()
    }

    sealed class Prohibition : PlaceResult() {
        data object DoubleThreeViolation : Prohibition()

        data object DoubleFourViolation : Prohibition()

        data object OverlineViolation : Prohibition()
    }
}
