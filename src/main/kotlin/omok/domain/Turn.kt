package omok.domain

data class Turn(private var color: StoneType = StoneType.BLACK) {
    fun next() {
        color =
            if (color == StoneType.BLACK) {
                StoneType.WHITE
            } else {
                StoneType.BLACK
            }
    }

    fun color(): StoneType = color

    fun isWhite(): Boolean = color == StoneType.WHITE
}
