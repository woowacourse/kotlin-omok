package domain.view

import domain.domain.state.State

interface Observer {
    fun update(state: State)
}
