package domain.domain

import domain.Position
import domain.domain.state.BlackTurn
import domain.domain.state.State
import domain.view.Observer

class Board : Observable {
    private var observers: Observer? = null
    var state: State = BlackTurn(Stones())
        private set

    override fun registerObserver(o: Observer) {
        observers = o
    }

    override fun removeObserver(o: Observer) {
        observers = null
    }

    override fun notifyObservers() {
        observers?.update(state)
    }

    fun next(position: Position) {
        state = state.toNextState(position)
        notifyObservers()
    }
}
