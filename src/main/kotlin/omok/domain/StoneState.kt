package omok.domain

import omok.domain.player.BlackPlayer
import omok.domain.player.Player
import omok.domain.player.WhitePlayer

enum class StoneState {
    BLACK,
    WHITE,
    BLANK,
    ;

    companion object {
        fun getColor(player: Player): StoneState {
            return when (player) {
                is BlackPlayer -> BLACK
                is WhitePlayer -> WHITE
                else -> throw IllegalStateException()
            }
        }
    }
}
