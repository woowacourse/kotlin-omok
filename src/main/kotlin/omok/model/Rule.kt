package omok.model

interface Rule {
    fun isInValid(
        stones: Stones,
        lastPlacedStone: Stone,
    ): Boolean

    fun isBlackWin(
        tones: Stones,
        lastPlacedPosition: Point,
    ): Boolean

    fun isWhiteWin(
        tones: Stones,
        lastPlacedPosition: Point,
    ): Boolean
}
