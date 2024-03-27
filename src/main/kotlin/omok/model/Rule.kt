package omok.model

interface Rule {
    fun isInvalid(
        stones: Stones,
        lastPlacedStone: Stone,
        customBoard: Array<Array<Int>>,
    ): Boolean
}
