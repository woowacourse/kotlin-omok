package omok.model

data class Point(val column: Int, val row: Int) {
    init {
        require(column in 0 until 15 && row in 0 until 15)
    }
}
