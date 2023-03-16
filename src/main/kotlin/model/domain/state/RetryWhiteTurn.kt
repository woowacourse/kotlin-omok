package model.domain.state

class RetryWhiteTurn : RetryTurn() {
    override fun retry(): State {
        return WhiteTurn()
    }
}
