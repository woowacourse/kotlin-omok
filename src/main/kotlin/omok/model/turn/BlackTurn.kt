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
import omok.model.rule.FourByFourRule
import omok.model.rule.OverSixInRowRule
import omok.model.rule.Rule
import omok.model.rule.ThreeByThreeRule

class BlackTurn(override val board: Board) : Turn {
    private val prohibitedRules: List<Rule> = listOf(ThreeByThreeRule, FourByFourRule, OverSixInRowRule)

    override fun placeStone(
        point: Point,
        onInappropriate: (String) -> Unit,
    ): Turn {
        val stone = Stone(point, StoneColor.BLACK)

        return when (val boardState: BoardState = board.place(stone)) {
            is Duplicated -> {
                onInappropriate(boardState.message)
                BlackTurn(board)
            }

            is OutOfRange -> {
                onInappropriate(boardState.message)
                BlackTurn(board)
            }

            is Full -> {
                onInappropriate(boardState.message)
                Finished(board, StoneColor.BLACK)
            }

            is Success -> {
                if (prohibitedRules.any { it.check(boardState.board) }) {
                    return BlackTurn(boardState.board)
                }
                if (FiveInRowRule.check(boardState.board)) return Finished(boardState.board, StoneColor.BLACK)
                WhiteTurn(boardState.board)
            }
        }
    }

    override fun color(): StoneColor = StoneColor.BLACK
}
