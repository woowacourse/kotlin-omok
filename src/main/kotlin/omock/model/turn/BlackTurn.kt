package omock.model.turn

import core.omock.rule.OMockRule
import omock.model.state.Stone
import omock.model.turn.ErrorType.FourToFourCount
import omock.model.turn.ErrorType.IsClearFourToFourCount
import omock.model.turn.ErrorType.IsReverseTwoAndThree
import omock.model.turn.ErrorType.ThreeToThreeCount

class BlackTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Turn() {
    override fun isFinished(): Boolean = false

    override fun processTurn(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Turn {
        OMockRule.run {
            if (threeToThreeCount(stoneStates, row, column)) throw IllegalArgumentException(ThreeToThreeCount())
            if (fourToFourCount(stoneStates, row, column)) throw IllegalArgumentException(FourToFourCount())
            if (isClearFourToFour(stoneStates, row, column)) throw IllegalArgumentException(IsClearFourToFourCount())
            if (isReverseTwoAndThree(stoneStates, row, column)) throw IllegalArgumentException(IsReverseTwoAndThree())
            if (isGameWon(stoneStates, row, column)) return FinishedTurn()
            return turnOff()
        }
    }
}
