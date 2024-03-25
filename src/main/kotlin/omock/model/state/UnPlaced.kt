package omock.model.state

import omock.model.turn.BlackTurn
import omock.model.turn.FinishedTurn
import omock.model.turn.Turn
import omock.model.turn.WhiteTurn
import java.lang.IllegalArgumentException

abstract class UnPlaced(private val stone: Stone) : StoneState {
    override fun put(turn: Turn): StoneState {
        return when (turn) {
            is BlackTurn -> Black(stone)
            is WhiteTurn -> White(stone)
            is FinishedTurn -> throw IllegalArgumentException("게임이 이미 종료되었습니다")
        }
    }

    override fun rollback(): StoneState {
        return Clear(stone)
    }

    companion object {
        const val CLEAR_NUMBER = 0
    }
}
