package omok.domain.model.state

import omok.domain.model.stone.StoneType

class OmokStateMachine {
    var state: OmokState = BlackStoneTurn
        private set

    fun transition(event: OmokEvent) {
        state =
            when (event) {
                is OmokEvent.WIN -> Finish(state.stoneType)
                is OmokEvent.DRAW -> Finish(StoneType.NONE)
                is OmokEvent.TURN -> state.updateState()
            }
    }
}
