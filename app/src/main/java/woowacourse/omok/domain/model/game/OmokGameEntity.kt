package woowacourse.omok.domain.model.game

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerName
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.DrawRule
import woowacourse.omok.domain.model.rule.judge.JudgeRules
import woowacourse.omok.domain.model.rule.judge.WinningRule
import woowacourse.omok.domain.model.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.model.rule.place.ExternalRule
import woowacourse.omok.domain.model.rule.place.InvalidPositionRule
import woowacourse.omok.domain.model.rule.place.PlaceRules

data class OmokGameEntity(
    val id: Int = DEFAULT_GAME_ID,
    val host: PlayerName = PlayerName.create(),
    val board: OmokBoard = OmokBoard.create(),
    private var lastTurn: StoneColor = StoneColor.BLACK,
    val placeRules: PlaceRules = PlaceRules(listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule())),
    val judgeRules: JudgeRules = JudgeRules(listOf(WinningRule(), DrawRule())),
) {
    val currentTurn: StoneColor get() = lastTurn

    fun reverseTurn() {
        lastTurn = lastTurn.reversed()
    }

    companion object {
        const val DEFAULT_GAME_ID = -1
    }
}
