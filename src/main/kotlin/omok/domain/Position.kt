package omok.domain

data class Position(val x: Int, val y: Int) {
    init {
        require(x in 0 until OmokBoard.DEFAULT_SIZE) {}
        require(y in 0 until OmokBoard.DEFAULT_SIZE) {}
    }
}
