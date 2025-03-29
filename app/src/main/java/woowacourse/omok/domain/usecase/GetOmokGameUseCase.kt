package woowacourse.omok.domain.usecase

import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.rule.judge.DrawRule
import woowacourse.omok.domain.model.rule.judge.JudgeRule
import woowacourse.omok.domain.model.rule.judge.WinningRule
import woowacourse.omok.domain.model.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.model.rule.place.ExternalRule
import woowacourse.omok.domain.model.rule.place.InvalidPositionRule
import woowacourse.omok.domain.model.rule.place.PlaceRule
import woowacourse.omok.domain.repository.OmokGameRepository

class GetOmokGameUseCase(
    private val omokGameRepository: OmokGameRepository,
) {
    suspend operator fun invoke(
        placeRule: List<PlaceRule> = listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule()),
        judgeRule: List<JudgeRule> = listOf(WinningRule(), DrawRule()),
    ): OmokGame {
        val savedGame = omokGameRepository.fetchGame()
        return OmokGame(savedGame.board, placeRule, judgeRule, savedGame.lastTurn)
    }
}
