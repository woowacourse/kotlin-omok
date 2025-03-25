package omok.domain.rule.renjuRule

import omok.domain.board.OmokBoard
import omok.domain.place.Place
import omok.domain.rule.OmokRule
import omok.domain.rule.finder.Finder

class RenjuRule(val finder: Finder) : OmokRule {
    override fun isProtected(
        place: Place,
        board: OmokBoard,
    ): Boolean {
        return ThreeThreeRule(finder).isProtected(place, board) ||
            FourFourRule(finder).isProtected(place, board) ||
            SixMokRule(finder).isProtected(place, board)
    }
}
