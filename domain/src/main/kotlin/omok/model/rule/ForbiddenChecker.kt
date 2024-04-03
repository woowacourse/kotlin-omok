package omok.model.rule

import omok.model.position.Position
import omok.model.result.PutResult

object ForbiddenChecker {
    const val EXCEED_FIVE_PRECONDITION = 5
    private val renjuRules: List<RenjuRule> = listOf(DoubleThreeChecker, DoubleFourChecker, ExceedFiveChecker(EXCEED_FIVE_PRECONDITION))

    fun checkForbidden(position: Position): PutResult {
        renjuRules.forEach {
            return when (val result = it.check(position)) {
                PutResult.DoubleThree -> result
                PutResult.DoubleFour -> result
                PutResult.ExceedFive -> result
                PutResult.Running -> PutResult.Running
                PutResult.Failure -> PutResult.Failure
                PutResult.Omok -> PutResult.Omok
            }
        }
        return PutResult.Running
    }
}
