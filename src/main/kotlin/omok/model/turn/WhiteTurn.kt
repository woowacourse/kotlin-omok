package omok.model.turn

import PlaceStoneInterrupt
import omok.model.Board
import omok.model.Either
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.flatmap
import omok.model.rule.FiveInRowRule

class WhiteTurn(
    board: Either<PlaceStoneInterrupt, Board>,
) : Turn(board) {
    override fun proceed(point: Point): Turn {
        val stone = Stone(point, StoneColor.WHITE)

        val placeStone: (board: Board) -> Either<PlaceStoneInterrupt, Board> = { it.place(stone) }

        val checkFinished: (board: Board) -> Either<PlaceStoneInterrupt, Board> = {
            if (it.isFull() || FiveInRowRule.check(it)) {
                Either.Left(PlaceStoneInterrupt.GameFinished(it))
            } else {
                Either.Right(it)
            }
        }

        val nextBoard: Either<PlaceStoneInterrupt, Board> = board flatmap placeStone flatmap checkFinished

        return BlackTurn(nextBoard)
    }

    override fun color(): StoneColor {
        return StoneColor.WHITE
    }
}
