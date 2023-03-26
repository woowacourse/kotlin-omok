package omok.judgement

import omok.Player
import omok.Position

class RenjuJudgement() : Judgement {

    override fun line(player: Player, position: Position): Boolean {
        return LineJudgement(player, position).check()
    }
    override fun isForbiddenMove(blackPlayer: Player, whitePlayer: Player, position: Position): Boolean {
        if (line(blackPlayer, position)) {
            return false
        }
        return FourJudgement(blackPlayer, whitePlayer, position).check() ||
            ThreeJudgement(blackPlayer, whitePlayer, position).check()
    }
}
