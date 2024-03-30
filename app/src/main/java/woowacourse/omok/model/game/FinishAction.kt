package woowacourse.omok.model.game

import woowacourse.omok.model.rule.finish.FinishCondition

interface FinishAction {
    val conditions: List<FinishCondition>

    fun onFinish(finishType: FinishType)
}
