package omok.domain.placeresult

import rule.type.Violation

sealed class Failure : PlaceResult {
    data object AlreadyExistStone : Failure()

    data object InvalidPosition : Failure()

    data class ExternalRenjuRule(
        val rule: Violation,
    ) : Failure()
}
