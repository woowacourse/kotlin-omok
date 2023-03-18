package domain.stone

data class Point(val x: Int, val y: Int) {

    init {
        require(x in 0..14 && y in 0..14) {
            // TODO: MESSAGE
        }
    }
}
