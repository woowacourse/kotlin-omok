package woowacourse.omok.domain.placeresult

import woowacourse.omok.domain.rule.LocalViolation

sealed class InvalidMove : PlaceResult {
    data object AlreadyExistStone : InvalidMove()

    data object InvalidPosition : InvalidMove()

    data class ExternalRenjuRule(
        val rule: LocalViolation,
    ) : InvalidMove()
}
