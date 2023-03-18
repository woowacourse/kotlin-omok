package domain

import domain.board.Board
import domain.library.result.WinningReferee
import domain.stone.Color

sealed class OmokGameState(open val winningColor: Color?) {

    object Running : OmokGameState(null)

    data class End(override val winningColor: Color) : OmokGameState(winningColor)

    companion object {
        private val winningReferee: WinningReferee = WinningReferee()

        fun valueOf(currentBoard: Board, color: Color): OmokGameState {
            if (winningReferee.checkWin(currentBoard.placedStones, color)) {
                return End(color)
            }
            return Running
        }
    }
}
