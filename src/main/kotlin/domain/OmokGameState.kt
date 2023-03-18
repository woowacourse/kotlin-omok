package domain

import domain.board.Board
import domain.library.result.WinningReferee
import domain.stone.Color

sealed class OmokGameState {

    object Running : OmokGameState()

    data class End(val winningColor: Color) : OmokGameState()

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
