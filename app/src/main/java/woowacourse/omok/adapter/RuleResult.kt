package woowacourse.omok.adapter

sealed class RuleResult {
    data object OnRule : RuleResult()

    data object DuplicatePosition : RuleResult()

    data object RenJuRule : RuleResult()
}
