package woowacourse.omok.dbHelper

import omok.domain.OmokBoard
import omok.domain.gameState.BlackTurn
import omok.domain.gameState.BlackWin
import omok.domain.gameState.GameState
import omok.domain.gameState.WhiteTurn
import omok.domain.gameState.WhiteWin

enum class GameStateDbModel(val value: Int) {
    BLACKTURN(1),
    BLACKWIN(2),
    WITHTURN(3),
    WHITEWIN(4),
    ;

    companion object {
        fun fromGameState(gameState: GameState): GameStateDbModel {
            return when (gameState) {
                is BlackTurn -> BLACKTURN
                is BlackWin -> BLACKWIN
                is WhiteTurn -> WITHTURN
                is WhiteWin -> WHITEWIN
            }
        }

        fun toGameState(value: Int, board: OmokBoard): GameState {
            return when (value) {
                1 -> BlackTurn(board)
                2 -> BlackWin(board)
                3 -> WhiteTurn(board)
                4 -> WhiteWin(board)
                else -> throw IllegalArgumentException("Invalid value: $value")
            }
        }
    }
}
