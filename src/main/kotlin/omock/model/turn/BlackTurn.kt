package omock.model.turn

import core.omock.rule.OMockRule
import omock.model.ErrorType.FourToFourCount
import omock.model.ErrorType.IsClearFourToFourCount
import omock.model.ErrorType.IsReverseTwoAndThree
import omock.model.ErrorType.ThreeToThreeCount
import omock.model.state.Stone

class BlackTurn(
    override val stoneHistory: ArrayDeque<Stone> = ArrayDeque(),
) : Turn() {
    override fun isFinished(): Boolean = false

    override fun processTurn(
        stoneStates: List<List<Int>>,
        row: Int,
        column: Int,
    ): Result<Turn> {
        OMockRule.run {
            if (threeToThreeCount(stoneStates, row, column)) return Result.failure(ThreeToThreeCount())
            if (fourToFourCount(stoneStates, row, column)) return Result.failure(FourToFourCount())
            if (isClearFourToFour(stoneStates, row, column)) return Result.failure(IsClearFourToFourCount())
            if (isReverseTwoAndThree(stoneStates, row, column)) return Result.failure(IsReverseTwoAndThree())
            if (isGameWon(stoneStates, row, column)) return Result.success(FinishedTurn())
            return Result.success(turnOff())
        }
    }
}
