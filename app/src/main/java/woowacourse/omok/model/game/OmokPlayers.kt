package woowacourse.omok.model.game

import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.player.Player
import woowacourse.omok.model.rule.ban.DoubleFourForbiddenPlace
import woowacourse.omok.model.rule.ban.DoubleOpenThreeForbiddenPlace
import woowacourse.omok.model.rule.ban.OverlineForbiddenPlace

class OmokPlayers(
    private val blackStonePlayer: Player =
        Player(
            Stone.BLACK,
            listOf(
                DoubleOpenThreeForbiddenPlace(),
                DoubleFourForbiddenPlace(),
                OverlineForbiddenPlace(),
            ),
        ),
    private val whiteStonePlayer: Player =
        Player(Stone.WHITE),
) {
    fun firstOrderPlayer() = blackStonePlayer

    fun next(recentPlayer: Player): Player {
        return if (recentPlayer == blackStonePlayer) whiteStonePlayer else blackStonePlayer
    }
}
