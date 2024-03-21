package omok.model.turn

import omok.model.Board
import omok.model.StoneAlreadyExists
import omok.model.StoneOutOfBoard
import omok.model.Success
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule

class WhiteTurn(board: Board) : Turn(board) {
    override fun placeStone(point: Point): Turn {
        val stone = Stone(point, StoneColor.WHITE)

        val nextBoard =
            when (val placeResult = board.place(stone)) {
                is StoneOutOfBoard, is StoneAlreadyExists -> return WhiteTurn(board)
                is Success -> placeResult.board
            }

        if (nextBoard.isFull() || FiveInRowRule.check(nextBoard)) return Finished(board)

        return BlackTurn(nextBoard)
    }

    override fun color(): StoneColor {
        return StoneColor.WHITE
    }
}
