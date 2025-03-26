package woowacourse.omok.domain.model.rule

import woowacourse.omok.domain.model.stone.Stones

sealed class PlaceResult {
    data class OnPlace(val stones: Stones) : PlaceResult()

    data class DuplicatePosition(val message: String) : PlaceResult()

    data class RenJuRule(val message: String) : PlaceResult()
}
