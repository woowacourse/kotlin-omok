package woowacourse.omok.view

import woowacourse.omok.model.GameState
import woowacourse.omok.model.player.Player

object InputView {
    private const val FIRST_INDEX_INPUT = 0
    private const val SECOND_INDEX_INPUT = 1

    fun playerPick(player: Player): GameState.LoadStone {
        return player.turn {
            OutputView.outputUserLocation()
            val input = readln().trim()
            Pair(
                input.substring(FIRST_INDEX_INPUT, SECOND_INDEX_INPUT),
                input.substring(
                    SECOND_INDEX_INPUT,
                ),
            )
        }
    }
}
