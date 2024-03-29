package omok.model.turn

import PlaceStoneInterrupt
import omok.model.Board
import omok.model.Either
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.flatmap
import omok.model.rule.FiveInRowRule
import omok.model.rule.FourByFourRule
import omok.model.rule.OverSixInRowRule
import omok.model.rule.Rule
import omok.model.rule.ThreeByThreeRule

class BlackTurn(
    board: Either<PlaceStoneInterrupt, Board>,
) : Turn(board) {
    private val prohibitedRules: List<Rule> = listOf(ThreeByThreeRule, FourByFourRule, OverSixInRowRule)

    override fun proceed(point: Point): Turn {
        val stone = Stone(point, StoneColor.BLACK)

        val placeStone: (board: Board) -> Either<PlaceStoneInterrupt, Board> = { it.place(stone) }

        val checkRules: (board: Board) -> Either<PlaceStoneInterrupt, Board> = {
            val isViolated = it.checkRulesAny(prohibitedRules)
            if (isViolated) {
                Either.Left(PlaceStoneInterrupt.StoneViolatedRules())
            } else {
                Either.Right(it)
            }
        }

        val checkFinished: (board: Board) -> Either<PlaceStoneInterrupt, Board> = {
            if (it.isFull() || FiveInRowRule.check(it)) {
                Either.Left(PlaceStoneInterrupt.GameFinished(it))
            } else {
                Either.Right(it)
            }
        }

        val nextBoard: Either<PlaceStoneInterrupt, Board> =
            board flatmap placeStone flatmap checkRules flatmap checkFinished

        return WhiteTurn(nextBoard)
    }

    override fun color(): StoneColor {
        return StoneColor.BLACK
    }
}
