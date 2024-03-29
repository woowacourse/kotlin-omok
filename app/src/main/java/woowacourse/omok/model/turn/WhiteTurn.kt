package woowacourse.omok.model.turn

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardState
import woowacourse.omok.model.board.Duplicated
import woowacourse.omok.model.board.Full
import woowacourse.omok.model.board.OutOfRange
import woowacourse.omok.model.board.Success
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.Stone
import woowacourse.omok.model.entity.StoneColor
import woowacourse.omok.model.rule.FiveInRowRule

class WhiteTurn(override val board: Board) : Turn {
    override fun placeStone(
        point: Point,
        onInappropriate: (String) -> Unit,
    ): Turn {
        val stone = Stone(point, StoneColor.WHITE)

        when (val boardState: BoardState = board.place(stone)) {
            is Duplicated -> {
                onInappropriate(boardState.message)
                return WhiteTurn(board)
            }

            is OutOfRange -> {
                onInappropriate(boardState.message)
                return WhiteTurn(board)
            }

            is Full -> return Finished(board, StoneColor.WHITE)
            is Success -> {
                return if (FiveInRowRule.check(boardState.board)) {
                    Finished(boardState.board, StoneColor.WHITE)
                } else {
                    BlackTurn(boardState.board)
                }
            }
        }
    }

    override fun color(): StoneColor = StoneColor.WHITE
}
