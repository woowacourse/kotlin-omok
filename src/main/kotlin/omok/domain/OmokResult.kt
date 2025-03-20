package omok.domain

import omok.domain.player.BlackPlayer
import omok.domain.player.Player
import omok.domain.player.WhitePlayer

enum class OmokResult {
    BLACK_WIN,
    WHITE_WIN,
    DRAW,
    ;

    companion object {
        fun getWinner(player: Player): OmokResult {
            return when (player) {
                is BlackPlayer -> BLACK_WIN
                is WhitePlayer -> WHITE_WIN
                else -> throw IllegalStateException()
            }
        }
    }
}
