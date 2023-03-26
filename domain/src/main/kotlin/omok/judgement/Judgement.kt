package omok.judgement

import omok.Player
import omok.Position

interface Judgement {
    fun line(player: Player, position: Position): Boolean

    fun isForbiddenMove(blackPlayer: Player, whitePlayer: Player, position: Position): Boolean
}
