package domain.domain

import domain.Position
import domain.domain.state.BlackTurn
import domain.domain.state.State
import domain.view.Observer

class Board : Observable {
    private var observer: Observer? = null
    var state: State = BlackTurn(Stones())
        private set

    override fun registerObserver(o: Observer) {
        observer = o
    }

    override fun removeObserver(o: Observer) {
        observer = null
    }

    override fun notifyObserver() {
        observer?.update(state)
    }

    fun next(position: Position) {
        state = state.toNextState(position)
        notifyObserver()
    }
}
