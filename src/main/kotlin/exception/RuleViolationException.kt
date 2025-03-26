package exception

import rule.type.Violation

class RuleViolationException(
    violation: Violation,
) : IllegalArgumentException(violation.message)
