package woowacourse.omok.domain.usecase

import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.rule.judge.DrawRule
import woowacourse.omok.domain.model.rule.judge.JudgeRules
import woowacourse.omok.domain.model.rule.judge.WinningRule
import woowacourse.omok.domain.model.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.model.rule.place.ExternalRule
import woowacourse.omok.domain.model.rule.place.InvalidPositionRule
import woowacourse.omok.domain.model.rule.place.PlaceRules
import woowacourse.omok.domain.repository.OmokGameRepository

class GetOmokGameUseCase(
    private val omokGameRepository: OmokGameRepository,
) {
    suspend operator fun invoke(
        placeRule: PlaceRules = PlaceRules(listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule())),
        judgeRule: JudgeRules = JudgeRules(listOf(WinningRule(), DrawRule())),
    ): OmokGame {
        val savedGame = omokGameRepository.fetchGame()
        return OmokGame(savedGame.board, placeRule, judgeRule, savedGame.lastTurn)
    }
}
