package omok.domain.rule.place

import omok.domain.player.PlayerStone
import omok.domain.rule.OmokResult

sealed class PlaceResult : OmokResult {
    data class Success(
        val lastStone: PlayerStone,
    ) : PlaceResult()

    sealed class Failure : PlaceResult() {
        data object AlreadyExistStone : Failure()

        data object InvalidPosition : Failure()

        data object DoubleThreeViolation : Failure()

        data object DoubleFourViolation : Failure()

        data object OverlineViolation : Failure()
    }
}
