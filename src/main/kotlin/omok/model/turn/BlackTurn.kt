package omok.model.turn

import omok.model.Board
import omok.model.Either
import omok.model.PlaceStoneError
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule
import omok.model.rule.FourByFourRule
import omok.model.rule.OverSixInRowRule
import omok.model.rule.Rule
import omok.model.rule.ThreeByThreeRule

class BlackTurn(
    board: Board,
) : Turn(board) {
    private val prohibitedRules: List<Rule> = listOf(ThreeByThreeRule, FourByFourRule, OverSixInRowRule)

    override fun placeStone(point: Point): Either<PlaceStoneError, Turn> {
        val stone = Stone(point, StoneColor.BLACK)

        val nextBoard =
            when (val placeResult = board.place(stone)) {
                is Either.Left -> return Either.Left(placeResult.value)
                is Either.Right -> placeResult.value
            }

        val isViolated =
            prohibitedRules.any {
                it.check(nextBoard)
            }
        if (isViolated) {
            return Either.Right(this)
        }

        if (nextBoard.isFull() || FiveInRowRule.check(nextBoard)) {
            return Either.Right(Finished(nextBoard))
        }

        return Either.Right(WhiteTurn(nextBoard))
    }

    override fun color(): StoneColor {
        return StoneColor.BLACK
    }
}

// 하고 싶은 것.
// Board map ::placeStone flatmap ::checkOutOfBoard flatmap ::checkProhibitedRule flatmap
// Board to Either<Error,Board> To Either<Error,Board>

// ::checkInvalid - Either<Error, Turn>, Left: Error, Right: Turn
// ::checkFinished - Either<Turn, Turn>, Left: Finished, Right: WhiteTurn
// 에러 던지기는 어떻게 하는가..
