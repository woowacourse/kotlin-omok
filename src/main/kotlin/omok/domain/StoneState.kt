package omok.domain

enum class StoneState {
    BLACK,
    WHITE,
    BLANK,
    ;

    companion object {
        fun changeTurn(nowTurn: StoneState): StoneState {
            return when (nowTurn) {
                BLACK -> WHITE
                WHITE -> BLACK
                BLANK -> throw IllegalStateException()
            }
        }
    }
}
