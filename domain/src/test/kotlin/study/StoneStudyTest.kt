package study

class StoneStudyTest() {

  /*  @Test
    fun `하얀돌이 대각선으로 5개가 이어져있을 때 백돌이 이긴다`() {
        val putWhiteStone = Stone(StonePosition.from(5, 5)!!, StoneType.WHITE)
        val whiteStones: WhiteStones = WhiteStones(
            listOf(정
                Stone(StonePosition.from(1, 1)!!, StoneType.WHITE),
                Stone(StonePosition.from(2, 2)!!, StoneType.WHITE),
                Stone(StonePosition.from(3, 3)!!, StoneType.WHITE),
                Stone(StonePosition.from(4, 4)!!, StoneType.WHITE),
            ),
        )

        Game(15).checkWinner(putWhiteStone.position.x, putWhiteStone.position.y)
    }
}

//class Game(private val boardSize: Int) {
//    private val board: Array<Array<Stone?>> = Array(boardSize) { Array(boardSize) { null } }
//
//    fun putStone(row: Int, col: Int, stone: Stone): Boolean {
//        if (board[row][col] != null) {
//            return false
//        }
//
//        board[row][col] = stone
//        return true
//    }
//
//    fun checkWinner(row: Int, col: Int): Boolean {
//        val stone = board[row][col] ?: return false
//
//        fun countStonesInDirection(dx: Int, dy: Int): Int {
//            var i = row + dx
//            var j = col + dy
//            var count = 0
//
//            while (i in 0 until boardSize && j in 0 until boardSize && board[i][j] == stone) {
//                count++
//                i += dx
//                j += dy
//            }
//
//            return count
//        }
//
//        return (
//                countStonesInDirection(-1, 0) + countStonesInDirection(1, 0) + 1 >= 5 ||
//                        countStonesInDirection(0, -1) + countStonesInDirection(0, 1) + 1 >= 5 ||
//                        countStonesInDirection(-1, -1) + countStonesInDirection(1, 1) + 1 >= 5 ||
//                        countStonesInDirection(-1, 1) + countStonesInDirection(1, -1) + 1 >= 5
//                )
//    }
//}



//-------------------------


class Game(val stones: Stones) {

    fun putStone(stone: Stone): Boolean {
        if (stones.contains(stone.position.x to stone.position.y)) {
            return false
        }

        stones.add(stone)
        return true
    }

    fun checkWinner(row: Int, col: Int): Boolean {
        val stoneSet = when {
            stones.contains(row to col) -> stones
            else -> return false
        }

        fun countStonesInDirection(dx: Int, dy: Int): Int {
            var i = row + dx
            var j = col + dy
            var count = 0

            while (i in 0 until boardSize && j in 0 until boardSize && (i to j) in stoneSet) {
                count++
                i += dx
                j += dy
            }

            return count
        }

        return (countStonesInDirection(-1, 0) + countStonesInDirection(1, 0) + 1 >= 5
                || countStonesInDirection(0, -1) + countStonesInDirection(0, 1) + 1 >= 5
                || countStonesInDirection(-1, -1) + countStonesInDirection(1, 1) + 1 >= 5
                || countStonesInDirection(-1, 1) + countStonesInDirection(1, -1) + 1 >= 5)
    }*/
}
