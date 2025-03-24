package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.point.Point
import omok.domain.rule.renju.FourByFour
import omok.domain.rule.renju.Renju
import omok.domain.rule.renju.SixMok
import omok.domain.rule.renju.ThreeByThree

class OmokRulesFacade(board: OmokBoard) : OmokRule {
    private val fourByFour: Renju = FourByFour(board)
    private val threeByThree: Renju = ThreeByThree(board)
    private val sixMok: Renju = SixMok(board)

    override fun renjuRulesValidation(point: Point): Boolean {
        return checkFourByFour(point) || checkThreeByThree(point) || checkSixMok(point)
    }

    private fun checkFourByFour(point: Point): Boolean = fourByFour.match(point)

    private fun checkThreeByThree(point: Point): Boolean = threeByThree.match(point)

    private fun checkSixMok(point: Point): Boolean = sixMok.match(point)
}
