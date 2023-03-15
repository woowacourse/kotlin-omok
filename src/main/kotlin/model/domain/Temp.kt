// import model.domain.Stone
//
// class Board(val system: Map<Int, MutableList<Stone>>) {
//    companion object {
//        private const val SIZE = 15
//        private const val CORRECTION_VALUE = 1
//
//        private val OMOK_BOARD: Map<Int, MutableList<Stone>> =
//            List(SIZE) { row ->
//                row to MutableList(SIZE) { Stone.EMPTY }
//            }.toMap()
//
//        fun create(): Board = Board(OMOK_BOARD)
//    }
//
//
//    private val lineOffsets = listOf(
//        Offset(-1, 0),  // up
//        Offset(1, 0),   // down
//        Offset(0, -1),  // left
//        Offset(0, 1),   // right
//        Offset(-1, -1), // up-left diagonal
//        Offset(-1, 1),  // up-right diagonal
//        Offset(1, -1),  // down-left diagonal
//        Offset(1, 1)    // down-right diagonal
//    )
//
//    private val jumpOffsets = listOf(
//        Offset(-2, 0),  // jump up
//        Offset(2, 0),   // jump down
//        Offset(0, -2),  // jump left
//        Offset(0, 2),   // jump right
//        Offset(-2, -2), // jump up-left diagonal
//        Offset(-2, 2),  // jump up-right diagonal
//        Offset(2, -2),  // jump down-left diagonal
//        Offset(2, 2)    // jump down-right diagonal
//    )
//
//    fun check33Rule(coord: Coordination, color: StoneColor): Boolean {
//        for (offset in lineOffsets) {
//            if (is33(coord, offset, color)) {
//                return true
//            }
//        }
//        return false
//    }
//
//    private fun is33(coord: Coordination, offset: Offset, color: StoneColor): Boolean {
//        val stones = board.filterValues { it.any { it.color == color } }
//        val cnt = stones.filter {
//            Coordination.isSameCoordination(coord + offset, it.key + offset)
//        }.count()
//        return cnt == 3 && isNotBlocked(coord + offset, color)
//    }
//
//    fun check44Rule(coord: Coordination, color: StoneColor): Boolean {
//        for (offset in lineOffsets) {
//            if (is44(coord, offset, color)) {
//                return true
//            }
//        }
//        return false
//    }
//
//    private fun is44(coord: Coordination, offset: Offset, color: StoneColor): Boolean {
//        val stones = board.filterValues { it.any { it.color == color } }
//        val cnt = stones.filter {
//            Coordination.isSameCoordination(coord + offset, it.key + offset)
//        }.count()
//        return cnt >= 4
//    }
//
//    private fun isNotBlocked(coord: Coordination, color: StoneColor): Boolean {
//        for (offset in jumpOffsets) {
//            val blockedCoord = coord + offset
//            if (blockedCoord.isValid() && board[blockedCoord]?.any { it.color == color } == true) {
//                return false
//            }
//        }
//        return true
//    }
//
//    fun check6Rule(coord: Coordination, color: StoneColor): Boolean {
//        for (offset in lineOffsets) {
//            val lineStones = getLineStones(coord, offset, color)
//            if (lineStones.size >= 6) {
//                return true
//            }
//        }
//
//        return false
//    }
//
//    private fun getLineStones(coord: Coordination, offset: Offset, color: StoneColor): List<Coordination> {
//        val stones = mutableListOf(coord)
//        var currentCoord = coord + offset
//
//        while (currentCoord.isValid() && board[currentCoord]?.lastOrNull()?.color == color) {
//            stones.add(currentCoord)
//            currentCoord += offset
//        }
//
//        return stones
//    }
// }
