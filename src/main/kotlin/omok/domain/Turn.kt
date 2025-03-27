package omok.domain

class Turn(private var current: StoneType = StoneType.BLACK) {
    fun currentPlayer(): StoneType = current

    fun switch() {
        current =
            when (current) {
                StoneType.BLACK -> StoneType.WHITE
                StoneType.WHITE -> StoneType.BLACK
                else -> throw IllegalStateException("유효하지 않은 턴 입니다.")
            }
    }

    fun isBlackTurn(): Boolean = current == StoneType.BLACK

    fun stone(inputPosition: Position): Stone {
        TODO("Not yet implemented")
    }

    fun next() {
        TODO("Not yet implemented")
    }

    fun isWhite(): Boolean {
        TODO("Not yet implemented")
    }
}
