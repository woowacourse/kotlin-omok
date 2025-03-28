package woowacourse.omok.domain.rule

import woowacourse.omok.domain.exception.ResultState
import woowacourse.omok.domain.point.Point

interface OmokRule {
    fun renjuRulesValidation(point: Point): ResultState<Unit>
}
