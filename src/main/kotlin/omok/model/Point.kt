package omok.model

data class Point(val x: Int, val y: Int) {
    init {
        require(x in 1..15 && y in 1..15)
    }
}
