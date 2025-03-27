package woowacourse.omok.domain.rule

import woowacourse.omok.domain.exception.RendjuExceptions
import woowacourse.omok.domain.point.Point

interface OmokRule {
    fun renjuRulesValidation(point: Point): RendjuExceptions?
}
