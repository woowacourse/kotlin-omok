package omok.model.rule

import lib.renjurule.ExceedFiveChecker
import lib.renjurule.FourFourChecker
import lib.renjurule.ThreeThreeChecker
import omok.model.result.PutResult
import omok.model.rule.RenjuRuleAdapter.changeBoardToLibBoard
import omok.model.stone.StoneType

object LenjuRuleChecker {
    // 금수, 같은 자리
    private val checkers = listOf(ThreeThreeChecker, FourFourChecker, ExceedFiveChecker)

    fun check(
        board: Array<Array<StoneType>>,
        row: Int,
        col: Int,
    ): PutResult {
        val libBoard = changeBoardToLibBoard(board)

        for (checker in checkers) {
            val result = checker.check(libBoard, row, col)
            if (!isRunningResult(result)) return result
        }
        return PutResult.Running
    }

    private fun isRunningResult(result: PutResult) = result == PutResult.Running
}
