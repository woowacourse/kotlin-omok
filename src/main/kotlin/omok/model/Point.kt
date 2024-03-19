package omok.model

data class Point(val column: Int, val row: Int) {
    init {
        require(column in 1..15 && row in 1..15)
    }
}
