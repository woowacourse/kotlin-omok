package woowacourse.omok.domain.rule

import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.Stone

interface ForbiddenMoveRule {
    fun checkViolation(
        stones: OmokStones,
        startStone: Stone,
    ): Violation
}
