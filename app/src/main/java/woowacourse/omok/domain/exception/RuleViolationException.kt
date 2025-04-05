package woowacourse.omok.domain.exception

import woowacourse.omok.domain.rule.lib.type.Violation

class RuleViolationException(
    violation: Violation,
) : IllegalArgumentException(violation.message)
