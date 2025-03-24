package omok.domain.turn

import omok.domain.StoneState

class TurnManager {
    var nowTurn: StoneState = StoneState.BLACK

    fun changeTurn() {
        nowTurn = if (nowTurn == StoneState.BLACK) StoneState.WHITE else StoneState.BLACK
    }
}
