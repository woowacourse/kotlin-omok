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
import woowacourse.omok.model.rule.FourByFourRule
import woowacourse.omok.model.rule.OverSixInRowRule
import woowacourse.omok.model.rule.Rule
import woowacourse.omok.model.rule.ThreeByThreeRule

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
