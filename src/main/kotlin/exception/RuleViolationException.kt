package exception

import rule.lib.type.Violation

class RuleViolationException(
    violation: Violation,
) : IllegalArgumentException(violation.message)
