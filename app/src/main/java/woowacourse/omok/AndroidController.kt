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
        End: print
    ): Boolean {
        return when (omokGame.progressTurn(position)) {
            ProgressState.ERROR -> {
                Error()
                false
            }
            ProgressState.END -> {
                End()
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
