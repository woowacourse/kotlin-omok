package omok.lib

interface GameRule {
    fun setupBoard(board: Array<Array<Int>>)

    fun isWinningMove(
        rowCoords: Int,
        columnCoords: Int,
        stone: Int,
    ): Boolean

    fun findForbiddenPositions(stone: Int): List<Pair<Int, Int>>

    fun isMoveAllowed(
        board: Array<Array<Int>>,
        rowCoords: Int,
        columnCoords: Int,
        stone: Int,
        forbiddenPositions: List<Pair<Int, Int>>,
    ): Boolean

    fun placeForRuleCheck(
        x: Int,
        y: Int,
        stone: Int,
    )
}
