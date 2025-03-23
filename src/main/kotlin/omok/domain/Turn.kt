package omok.domain

data class Turn(private var turn: StoneType = StoneType.BLACK) {
    fun next() {
        turn =
            if (turn == StoneType.BLACK) {
                StoneType.WHITE
            } else {
                StoneType.BLACK
            }
    }

    fun isWhite(): Boolean = turn == StoneType.WHITE

    fun stone(inputPosition: String): Stone {
        val position = Position.from(inputPosition) ?: throw IllegalArgumentException("유효하지 않은 위치입니다. 다시 입력해주세요.")
        val stone = Stone(position, turn)
        return stone
    }
}
