package omok.model

import rule.OmokRule
import rule.WhiteRenjuRule
import rule.wrapper.point.Point

class WhitePlayer : Player() {
    override val points = mutableListOf<Point>()
    override val rule: OmokRule = WhiteRenjuRule()
}
