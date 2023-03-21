package omok.judgement

import omok.Player
import omok.Position

object Judgement {
    fun line(player: Player, position: Position): Boolean {
        return LineJudgement(player, position).check()
    }

    fun isForbiddenMove(blackPlayer: Player, whitePlayer: Player, position: Position): Boolean {
        if (line(blackPlayer, position)) {
            return false
        }
        return ThreeJudgement(blackPlayer, whitePlayer, position).check() ||
            FourJudgement(blackPlayer, whitePlayer, position).check()
    }
}
