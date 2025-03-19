package omok.domain

enum class OmokResult {
    BLACKWIN,
    WHITEWIN,
    DRAW,
    ;

    companion object {
        fun returnWinner(state: StoneState): OmokResult {
            return when (state) {
                StoneState.BLACK -> BLACKWIN
                StoneState.WHITE -> WHITEWIN
                StoneState.BLANK -> throw IllegalStateException()
            }
        }
    }
}
