package domain.stone

import rule.wrapper.point.Point

class Board(board: List<List<StoneType>> = List(16) { List(16) { StoneType.EMPTY } }) {

    private val _board = board.map { it.toMutableList() }.toMutableList()
    val board: List<List<StoneType>>
        get() = _board.map { it.toList() }.toList()

    val stones: Stones = Stones()

    val blackStonesPosition: List<Point>
        get() = getStonesPosition(StoneType.BLACK)

    val whiteStonesPosition: List<Point>
        get() = getStonesPosition(StoneType.WHITE)

    fun putStone(stone: Stone) {
        _board[stone.point.row][stone.point.col] = when (_board[stone.point.row][stone.point.col]) {
            StoneType.EMPTY -> stone.type
            else -> throw IllegalStateException()
        }
        stones.add(stone)
    }

    private fun getStonesPosition(stoneType: StoneType): List<Point> {
        return stones.getStonesPosition(stoneType)
    }
}
