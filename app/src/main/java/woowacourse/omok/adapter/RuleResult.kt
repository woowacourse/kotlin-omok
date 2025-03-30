package woowacourse.omok.adapter

import woowacourse.omok.domain.model.stone.Stone

sealed class RuleResult {
    data class OnRule(val stone: Stone) : RuleResult()

    data object DuplicatePosition : RuleResult()

    data object RenJuRule : RuleResult()
}
