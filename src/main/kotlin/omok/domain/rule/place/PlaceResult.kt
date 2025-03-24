package omok.domain.rule.place

import omok.domain.player.PlayerStone
import omok.domain.rule.winning.GameResult

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

        data object DoubleThreeViolation : Failure()

        data object DoubleFourViolation : Failure()

        data object OverlineViolation : Failure()
    }
}
