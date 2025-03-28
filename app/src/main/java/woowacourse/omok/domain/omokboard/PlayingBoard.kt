package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.rule.RuleNavigation

class PlayingBoard(
    var board: OmokBoard = OmokBoard(),
    private val ruleNavigation: RuleNavigation,
) {
    fun placeStone(playerStone: PlayerStone): PlaceResult = placeResult(playerStone)

    private fun placeResult(playerStone: PlayerStone): PlaceResult {
        val result = ruleNavigation.applyRules(board, playerStone)

        if (result is GameOnGoing) {
            board = board.updateBoard(playerStone)
        }

        return result
    }
}
