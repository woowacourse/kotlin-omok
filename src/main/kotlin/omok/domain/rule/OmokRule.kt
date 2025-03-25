package omok.domain.rule

import omok.domain.point.Point

interface OmokRule {
    fun renjuRulesValidation(point: Point): Boolean
}
