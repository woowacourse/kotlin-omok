package woowacourse.omok.domain.rule

import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.InvalidMove
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

class RuleNavigation(private val whiteRules: List<OmokRule>, private val blackRules: List<OmokRule>) {
    fun applyRules(
        board: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult {
        val rules = if (playerStone.color == StoneColor.WHITE) whiteRules else blackRules

        var result: PlaceResult = GameOnGoing

        rules.forEach { rule ->
            result = rule.place(board, playerStone)
            if (result is InvalidMove) return result
        }

        return result
    }
}
