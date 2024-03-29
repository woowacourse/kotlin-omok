package omok.model.turn

import omok.model.Board
import omok.model.Either
import omok.model.PlaceStoneError
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule

class WhiteTurn(
    board: Board,
) : Turn(board) {
    override fun placeStone(point: Point): Either<PlaceStoneError, Turn> {
        val stone = Stone(point, StoneColor.WHITE)

        val nextBoard =
            when (val placeResult = board.place(stone)) {
                is Either.Left -> return Either.Left(placeResult.value)
                is Either.Right -> placeResult.value
            }

        if (nextBoard.isFull() || FiveInRowRule.check(nextBoard)) {
            return Either.Right(Finished(nextBoard))
        }

        return Either.Right(BlackTurn(nextBoard))
    }

    override fun color(): StoneColor {
        return StoneColor.WHITE
    }
}
