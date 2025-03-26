package omok.domain.rule

import omok.domain.stone.OmokStones
import omok.domain.stone.Stone

interface ForbiddenMoveRule {
    fun checkViolation(
        stones: OmokStones,
        startStone: Stone,
    ): Violation
}
