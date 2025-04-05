package woowacourse.omok.domain

class Turn {
    var currentPlayer: StoneType = StoneType.BLACK

    val current: StoneType
        get() = currentPlayer

    fun validate(player: StoneType) {
        if (player != currentPlayer) {
            throw TurnViolationException(currentPlayer)
        }
    }

    fun switch() {
        currentPlayer =
            when (currentPlayer) {
                StoneType.BLACK -> StoneType.WHITE
                StoneType.WHITE -> StoneType.BLACK
                else -> throw IllegalStateException("유효하지 않은 턴입니다.")
            }
    }
}
