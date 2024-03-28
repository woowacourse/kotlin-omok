package omok.model.turn

import PlaceStoneInterrupt
import omok.model.Board
import omok.model.Either
import omok.model.entity.Point
import omok.model.entity.StoneColor

class Finished private constructor(board: Either<PlaceStoneInterrupt, Board>) : Turn(board) {
    constructor(board: Board) : this(Either.Right(board))

    override fun proceed(point: Point): Turn {
        throw IllegalStateException()
    }

    override fun color(): StoneColor {
        val stone = (board as Either.Right).value.previousStone() ?: return StoneColor.BLACK
        return stone.stoneColor
    }
}
