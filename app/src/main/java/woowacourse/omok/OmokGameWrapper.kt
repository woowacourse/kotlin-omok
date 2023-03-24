package woowacourse.omok

import domain.domain.*
import domain.library.combinerule.CombinedRuleAdapter
import woowacourse.omok.util.print

class OmokGameWrapper(val omokGame: OmokGame = OmokGame(gameRule = CombinedRuleAdapter())) {

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
