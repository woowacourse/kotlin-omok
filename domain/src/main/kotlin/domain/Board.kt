package domain.domain

import domain.Position
import domain.domain.state.BlackTurn
import domain.domain.state.State
import domain.view.Observer

class Board : Observable {
    private var observer: Observer? = null
    var state: State = BlackTurn(Stones())
        private set

    override fun registerObserver(observer: Observer) {
        this.observer = observer
    }

    override fun removeObserver(observer: Observer) {
        this.observer = null
    }

    override fun notifyObserver() {
        observer?.update(state)
    }

    fun next(position: Position) {
        state = state.toNextState(position)
        notifyObserver()
    }
}
