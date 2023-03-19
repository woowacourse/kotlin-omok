package omok.domain.judgement

import omok.domain.Player
import omok.domain.Position

object Judgement {
    fun line(player: Player, position: Position): Boolean {
        return LineJudgement(player, position).check()
    }

    fun threeOrFour(blackPlayer: Player, whitePlayer: Player, position: Position): Boolean {
        return ThreeJudgement(blackPlayer, whitePlayer, position).check() ||
            FourJudgement(blackPlayer, whitePlayer, position).check()
    }
}
