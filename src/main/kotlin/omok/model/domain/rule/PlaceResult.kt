package omok.model.domain.rule

import omok.model.domain.player.PlayerStone

sealed class PlaceResult {
    sealed class Success : PlaceResult() {
        data class Progress(
            val lastStone: PlayerStone,
        ) : Success()

        data class Finish(
            val result: GameResult,
        ) : Success()
    }

    sealed class Failure : PlaceResult() {
        data object AlreadyExistStone : Failure()

        data object InvalidPosition : Failure()

        data object DoubleThreeViolation : Failure()

        data object DoubleFourViolation : Failure()

        data object OverlineViolation : Failure()
    }
}
