package omock.view

import omock.model.state.Stone
import omock.model.turn.Turn

object InputView {
    private const val FIRST_INDEX_INPUT = 0
    private const val SECOND_INDEX_INPUT = 1

    fun playerPick(player: Turn): Result<Stone> {
        return runCatching {
            player.turn {
                OutputView.outputUserLocation()
                val input = readln().trim()
                Pair(input.substring(FIRST_INDEX_INPUT, SECOND_INDEX_INPUT), input.substring(SECOND_INDEX_INPUT))
            }
        }
    }
}
