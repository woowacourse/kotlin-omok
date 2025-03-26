package woowacourse.omok.domain.placeresult

import rule.type.Violation

sealed class InvalidMove : PlaceResult {
    data object AlreadyExistStone : InvalidMove()

    data object InvalidPosition : InvalidMove()

    data class ExternalRenjuRule(
        val rule: Violation,
    ) : InvalidMove()
}
