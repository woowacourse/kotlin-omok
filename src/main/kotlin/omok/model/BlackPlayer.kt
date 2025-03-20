package omok.model

import rule.BlackRenjuRule
import rule.OmokRule
import rule.wrapper.point.Point

class BlackPlayer : Player() {
    override val points = mutableListOf<Point>()
    override val rule: OmokRule = BlackRenjuRule()
}
