package woowacourse.omok.controller

import woowacourse.omok.model.rule.PlacementError
import woowacourse.omok.model.stone.StoneColor

sealed class GameState {
    data object Playing : GameState()

    data class Violation(
        val error: PlacementError,
    ) : GameState()

    data class Win(
        val winner: StoneColor?,
    ) : GameState()
}
