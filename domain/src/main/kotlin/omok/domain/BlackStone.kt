package omok.domain

import omok.domain.judgement.FourJudgement
import omok.domain.judgement.ThreeJudgement

class BlackStone(val position: Position) : Stone {

    override fun findPosition(value: Position) = (position == value)

    override fun getLocation() = position

    fun judgePossibility(blackPlayer: Player, whitePlayer: Player): Boolean {
        return ThreeJudgement(blackPlayer, whitePlayer, position).check() || FourJudgement(blackPlayer, whitePlayer, position).check()
    }
}
