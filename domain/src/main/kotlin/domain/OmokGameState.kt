package domain

import domain.board.Board
import domain.library.result.FiveStoneWinningReferee
import domain.library.result.WinningReferee
import domain.stone.Color
import domain.stone.Stone

sealed class OmokGameState {

    object Running : OmokGameState()

    data class End(val winningColor: Color) : OmokGameState()

    fun getResult(): Color {
        if (this is End) {
            return winningColor
        }
        throw IllegalStateException(ERROR_RESULT)
    }

    companion object {

        private const val ERROR_RESULT = "[ERROR] 게임이 종료되지 않았습니다."

        fun valueOf(
            currentBoard: Board,
            color: Color,
            winningReferee: WinningReferee = FiveStoneWinningReferee()
        ): OmokGameState {
            if (winningReferee.checkWin(currentBoard.placedStones, color)) {
                return End(color)
            }
            return Running
        }
    }
}
