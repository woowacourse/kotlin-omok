package woowacourse.omok.model.game

import woowacourse.omok.model.rule.PlacementError
import woowacourse.omok.model.stone.StoneColor

sealed class PlayResult {
    data object Success : PlayResult()

    data class Violation(
        val error: PlacementError,
    ) : PlayResult()

    data class Win(
        val winner: StoneColor,
    ) : PlayResult()
}
