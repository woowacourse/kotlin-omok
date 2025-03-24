package omok.domain.placeresult

import rule.type.Violation

sealed class GameNotProgress : PlaceResult {
    data object AlreadyExistStone : GameNotProgress()

    data object InvalidPosition : GameNotProgress()

    data class ExternalRenjuRule(
        val rule: Violation,
    ) : GameNotProgress()
}
