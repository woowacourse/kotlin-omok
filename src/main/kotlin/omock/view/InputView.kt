package omock.view

import omock.model.player.Player
import omock.model.stone.Stone

object InputView {
    private const val FIRST_INDEX_INPUT = 0
    private const val SECOND_INDEX_INPUT = 1

    fun playerPick(player: Player): Result<Stone> {
        return runCatching {
            player.turn {
                OutputView.outputUserLocation()
                val input = readln().trim()
                Pair(input.substring(FIRST_INDEX_INPUT, SECOND_INDEX_INPUT), input.substring(SECOND_INDEX_INPUT))
            }
        }
    }
}
