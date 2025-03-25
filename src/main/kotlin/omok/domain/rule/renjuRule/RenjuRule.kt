package omok.domain.rule.renjuRule

import omok.domain.board.OmokBoard
import omok.domain.rule.OmokRule
import omok.domain.rule.finder.Finder
import omok.domain.stone.Stone

class RenjuRule(val finder: Finder) : OmokRule {
    override fun isProtected(
        stone: Stone,
        board: OmokBoard,
    ): Boolean {
        return ThreeThreeRule(finder).isProtected(stone, board) ||
            FourFourRule(finder).isProtected(stone, board) ||
            SixMokRule(finder).isProtected(stone, board)
    }
}
