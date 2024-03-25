package omok.model

interface Rule {
    fun isInValid(
        stones: Stones,
        lastPlacedStone: Stone,
        customBoard: Array<Array<Int>>,
    ): Boolean
}
