package omok.domain

enum class OmokResult {
    BLACK_WIN,
    WHITE_WIN,
    DRAW,
    ;

    companion object {
        fun getWinner(stoneColor: StoneState): OmokResult {
            return when (stoneColor) {
                StoneState.BLACK -> BLACK_WIN
                StoneState.WHITE -> WHITE_WIN
                else -> throw IllegalStateException()
            }
        }
    }
}
