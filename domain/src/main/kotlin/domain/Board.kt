package domain.domain

import domain.Position
import domain.domain.state.BlackTurn
import domain.domain.state.State
import domain.view.Observer

class Board : Observable {
    private var observers = mutableListOf<Observer>()
    var state: State = BlackTurn(Stones())
        private set

    override fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObserver() {
        observers.forEach { it.update(state) }
    }

    fun next(position: Position) {
        state = state.toNextState(position)
        notifyObserver()
    }
}
