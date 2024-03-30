package woowacourse.omok

import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.rule.finish.FinishCondition

interface FinishAction {
    val conditions: List<FinishCondition>
    fun onFinish(finishType: FinishType)
}
