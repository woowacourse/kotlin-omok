package omok.domain.rule

import omok.domain.stone.Stone

interface ForbiddenMoveRule {
    fun checkViolation(
        stones: Set<Stone>,
        startStone: Stone,
    ): Violation
}
