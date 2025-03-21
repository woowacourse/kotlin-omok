package omok.domain

data class Position(val x: Int, val y: Int) {
    var stoneState: StoneState = StoneState.BLANK
        private set

    init {
        require(x in 1..OmokBoard.DEFAULT_SIZE) {}
        require(y in 1..OmokBoard.DEFAULT_SIZE) {}
    }

    fun changeState(state: StoneState): Boolean {
        if (stoneState == StoneState.BLANK) {
            stoneState = state
            return true
        }
        return false
    }
}
