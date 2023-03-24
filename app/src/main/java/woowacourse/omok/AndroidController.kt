package woowacourse.omok

import domain.domain.*
import domain.library.combinerule.CombinedRuleAdapter

class AndroidController {

    lateinit var omokGame: OmokGame
    fun run() {
        omokGame = OmokGame(gameRule = CombinedRuleAdapter())
    }

    fun progressGame(
        position: Position,
        Error: print,
        Request: print,
        End: (CoordinateState) -> Unit
    ): Boolean {
        return when (omokGame.progressTurn(position)) {
            ProgressState.ERROR -> {
                Error()
                false
            }
            ProgressState.END -> {
                End(omokGame.turn)
                true
            }
            ProgressState.CONTINUE -> {
                Request()
                true
            }
        }
    }
}

typealias print = () -> Unit
