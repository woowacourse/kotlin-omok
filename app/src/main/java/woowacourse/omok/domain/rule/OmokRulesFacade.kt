package woowacourse.omok.domain.rule

import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.exception.execute
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.rule.renju.FourByFour
import woowacourse.omok.domain.rule.renju.Renju
import woowacourse.omok.domain.rule.renju.SixMok
import woowacourse.omok.domain.rule.renju.ThreeByThree

class OmokRulesFacade(board: OmokBoard) : OmokRule {
    private val fourByFour: Renju = FourByFour(board)
    private val threeByThree: Renju = ThreeByThree(board)
    private val sixMok: Renju = SixMok(board)

    override fun checkRenjuRuleViolations(point: Point) =
        execute {
            checkFourByFour(point)
            checkThreeByThree(point)
            checkSixMok(point)
        }

    private fun checkFourByFour(point: Point) = fourByFour.match(point)

    private fun checkThreeByThree(point: Point) = threeByThree.match(point)

    private fun checkSixMok(point: Point) = sixMok.match(point)
}
