package woowacourse.omok.domain.model.omokboard

import woowacourse.omok.domain.model.player.PlayerName
import woowacourse.omok.domain.model.rule.judge.DrawRule
import woowacourse.omok.domain.model.rule.judge.JudgeRules
import woowacourse.omok.domain.model.rule.judge.WinningRule
import woowacourse.omok.domain.model.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.model.rule.place.ExternalRule
import woowacourse.omok.domain.model.rule.place.InvalidPositionRule
import woowacourse.omok.domain.model.rule.place.PlaceRules

data class OmokGameEntity(
    val id: Int = DEFAULT_GAME_ID,
    val host: PlayerName = PlayerName(),
    val board: OmokBoard = OmokBoard.create(),
    val placeRules: PlaceRules = PlaceRules(listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule())),
    val judgeRules: JudgeRules = JudgeRules(listOf(WinningRule(), DrawRule())),
) {
    companion object {
        private const val DEFAULT_GAME_ID = 0
    }
}
