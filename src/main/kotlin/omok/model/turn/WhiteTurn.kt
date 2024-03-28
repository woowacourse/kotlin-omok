package omok.model.turn

import omok.model.Board
import omok.model.Either
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule

class WhiteTurn(
    board: Board,
) : Turn(board) {
    override fun placeStone(point: Point, printError: (String) -> Unit): Turn {
        val stone = Stone(point, StoneColor.WHITE)

        val nextBoard =
            when (val placeResult = board.place(stone)) {
                is Either.Left -> {
                    printError(placeResult.value.errorMessage)
                    return this
                }
                is Either.Right -> placeResult.value
            }

        if (nextBoard.isFull() || FiveInRowRule.check(nextBoard)) {
            return Finished(nextBoard)
        }

        return BlackTurn(nextBoard)
    }

    override fun color(): StoneColor {
        return StoneColor.WHITE
    }
}
