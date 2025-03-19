package omok.domain

enum class OmokResult {
    BLACK_WIN,
    WHITE_WIN,
    DRAW,
    ;

    companion object {
        fun returnWinner(player: Player): OmokResult {
            return when (player) {
                is BlackPlayer -> BLACK_WIN
                is WhitePlayer -> WHITE_WIN
                else -> throw IllegalStateException()
            }
        }
    }
}
