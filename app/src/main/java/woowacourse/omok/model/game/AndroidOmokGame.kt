package woowacourse.omok.model.game

import woowacourse.omok.model.omok.Board
import woowacourse.omok.model.omok.OmokStone
import woowacourse.omok.model.omok.Position
import woowacourse.omok.model.omok.StoneColor
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.model.rule.StonePlaceRule
import woowacourse.omok.model.state.BlackTurn
import woowacourse.omok.model.state.GameState
import woowacourse.omok.model.state.WhiteTurn

class AndroidOmokGame(
    private var state: GameState = BlackTurn(RenjuRule, Board()),
    private val onStateChanged: (position: Position) -> Unit,
    private val onFinishGame: (Board, OmokStone) -> Unit,
) {
    fun placeOmokStone(position: Position) {
        val newState = state.put(position)
        if (state != newState) {
            state = newState
            state.board[position]?.run {
                onStateChanged(position)
            }
        }
        checkIsFinish()
    }

    private fun checkIsFinish() {
        if (state.isFinished) {
            state.board.lastStone?.let {
                onFinishGame(state.board, it)
            }
        }
    }

    fun restoreGame(
        color: StoneColor,
        board: Board,
    ) {
        state = mapStoneColorToGameState(color, board)
    }

    private fun mapStoneColorToGameState(
        color: StoneColor,
        board: Board,
    ): GameState {
        return when (color) {
            StoneColor.BLACK -> WhiteTurn(StonePlaceRule(), board)
            StoneColor.WHITE -> BlackTurn(RenjuRule, board)
        }
    }

    fun resetGame() {
        state = BlackTurn(RenjuRule, Board())
    }

    fun getStoneByPosition(position: Position): OmokStone? {
        return state.board[position]
    }
}
