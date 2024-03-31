package omok.model

interface Rule {
    fun isInValid(
        stones: List<Stone>,
        lastPlacedStone: Stone,
    ): Boolean

    fun isBlackWin(
        stones: List<Stone>,
        lastPlacedPosition: Point,
    ): Boolean

    fun isWhiteWin(
        stones: List<Stone>,
        lastPlacedPosition: Point,
    ): Boolean
}
