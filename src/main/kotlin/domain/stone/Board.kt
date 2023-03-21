package domain.stone

import rule.wrapper.point.Point

class Board(board: List<List<StoneType>> = List(16) { List(16) { StoneType.EMPTY } }) {

    private val _board = board.map { it.toMutableList() }.toMutableList()
    val board: List<List<StoneType>>
        get() = _board.map { it.toList() }.toList()

    val stones: Stones = Stones()

    fun putStone(stone: Stone) {
        _board[stone.point.row][stone.point.col] = when (_board[stone.point.row][stone.point.col]) {
            StoneType.EMPTY -> stone.type
            else -> throw IllegalStateException("오류")
        }
        stones.add(stone)
    }

    fun blackStonesPoint(): List<Point> = getStonesPoint(StoneType.BLACK)
    fun whiteStonesPoint(): List<Point> = getStonesPoint(StoneType.WHITE)

    private fun getStonesPoint(stoneType: StoneType): List<Point> {
        return stones.getStonesPosition(stoneType)
    }
}