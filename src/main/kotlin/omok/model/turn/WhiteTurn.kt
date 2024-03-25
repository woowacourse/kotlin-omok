package omok.model.turn

import omok.model.board.Board
import omok.model.board.BoardState
import omok.model.board.Duplicated
import omok.model.board.Full
import omok.model.board.OutOfRange
import omok.model.board.Success
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule

class WhiteTurn(override val board: Board) : Turn {
    override fun placeStone(
        point: Point,
        onInappropriate: (String) -> Unit,
    ): Turn {
        val stone = Stone(point, StoneColor.WHITE)

        return when (val boardState: BoardState = board.place(stone)) {
            is Duplicated -> {
                onInappropriate(boardState.message)
                WhiteTurn(board)
            }

            is OutOfRange -> {
                onInappropriate(boardState.message)
                WhiteTurn(board)
            }

            is Full -> Finished(board, StoneColor.WHITE)
            is Success -> {
                if (FiveInRowRule.check(boardState.board)) Finished(boardState.board, StoneColor.WHITE)
                BlackTurn(boardState.board)
            }
        }
    }

    override fun color(): StoneColor = StoneColor.WHITE
}
