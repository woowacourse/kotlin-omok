package model.domain.state

class RetryBlackTurn : RetryTurn() {

    override fun retry(): State {
        return BlackTurn()
    }
}
