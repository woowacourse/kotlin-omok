package model.domain.state.black

import model.domain.state.RetryTurn
import model.domain.state.State

class RetryBlackTurn : RetryTurn() {

    override fun retry(): State {
        return BlackTurn()
    }
}
