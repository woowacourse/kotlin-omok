package omok.domain

enum class OmokResult {
    BLACKWIN,
    WHITEWIN,
    DRAW,
    ;

    companion object {
        fun returnWinner(player: Player): OmokResult {
            return when (player) {
                is BlackPlayer -> BLACKWIN
                is WhitePlayer -> WHITEWIN
                else -> throw IllegalStateException()
            }
        }
    }
}
