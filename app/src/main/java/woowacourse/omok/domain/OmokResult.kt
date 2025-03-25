package woowacourse.omok.domain

enum class OmokResult {
    BLACK_WIN,
    WHITE_WIN,
    DRAW,
    ;

    companion object {
        fun getWinner(stoneColor: StoneColor): OmokResult {
            return when (stoneColor) {
                StoneColor.BLACK -> BLACK_WIN
                StoneColor.WHITE -> WHITE_WIN
            }
        }
    }
}
