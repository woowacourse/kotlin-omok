package model.domain.state.white

import model.domain.state.RetryTurn
import model.domain.state.State

class RetryWhiteTurn : RetryTurn() {
    override fun retry(): State {
        return WhiteTurn()
    }
}
