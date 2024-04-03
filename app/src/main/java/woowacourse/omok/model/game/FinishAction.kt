package woowacourse.omok.model.game

import woowacourse.omok.model.rule.finish.AllForbiddenPositionFinishCondition
import woowacourse.omok.model.rule.finish.FinishCondition
import woowacourse.omok.model.rule.finish.FiveStonesFinishCondition
import woowacourse.omok.model.rule.finish.FullBoardFinishCondition

interface FinishAction {
    fun conditions(): List<FinishCondition> {
        return listOf(
            FiveStonesFinishCondition(),
            FullBoardFinishCondition(),
            AllForbiddenPositionFinishCondition(),
        )
    }

    fun onFinish(finishType: FinishType)
}
