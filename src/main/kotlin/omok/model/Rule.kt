package omok.model

interface Rule {
    fun isInValid(
        stones: Stones,
        lastPlacedStone: Stone,
    ): Boolean

    fun isThreeThree(
        board: List<List<Int>>,
        stone: Stone,
    ): Boolean

    fun isFourFour(
        board: List<List<Int>>,
        stone: Stone,
    ): Boolean

    fun isMoreThanFive(
        board: List<List<Int>>,
        stone: Stone,
    ): Boolean
}
