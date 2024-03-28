package omok.view

import omok.model.state.Stone
import omok.model.turn.Turn

object InputView {
    private const val FIRST_INDEX_INPUT = 0
    private const val SECOND_INDEX_INPUT = 1

    fun playerPick(turn: Turn): Result<Stone> {
        return runCatching {
            turn.turn {
                OutputView.outputUserLocation()
                val input = readln().trim()
                Pair(input.substring(FIRST_INDEX_INPUT, SECOND_INDEX_INPUT), input.substring(SECOND_INDEX_INPUT))
            }
        }
    }
}
