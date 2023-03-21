package domain.rule

object OmokRule {

    /*const val MIN_OPEN_THREES = 2
    const val MIN_OPEN_FOURS = 2
    private const val MIN_X = 1
    private const val MAX_X = 15
    private const val MIN_Y = 1
    private const val MAX_Y = 15
    private val X_Edge = listOf(MIN_X, MAX_X)
    private val Y_Edge = listOf(MIN_Y, MAX_Y)

    val directions = listOf(Pair(1, 0), Pair(1, 1), Pair(0, 1), Pair(1, -1))

    fun validateThrees(board: List<List<StoneType>>, stone: Stone): Boolean =
        countOpenThrees(board, stone) >= 2

    private fun countOpenThrees(board: List<List<StoneType>>, stone: Stone): Int =
        directions.sumOf { direction -> checkOpenThree(board, stone, direction.first, direction.second) }

    fun validateFour(board: List<List<StoneType>>, stone: Stone): Boolean =
        countOpenFours(board, stone) >= 2

    private fun countOpenFours(board: List<List<StoneType>>, stone: Stone): Int =
        directions.sumOf { direction -> checkOpenFour(board, stone, direction.first, direction.second) }

    private fun checkOpenThree(board: List<List<StoneType>>, stone: Stone, dx: Int, dy: Int): Int {
        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        return when {
            stone1 + stone2 != 2 -> 0
            blink1 + blink2 == 2 -> 0
            dx != 0 && stone.position.x - dx * leftDown in X_Edge -> 0
            dy != 0 && stone.position.y - dy * leftDown in Y_Edge -> 0
            dx != 0 && stone.position.x + dx * rightUp in X_Edge -> 0
            dy != 0 && stone.position.y + dy * rightUp in Y_Edge -> 0
            board[stone.position.y - down][stone.position.x - left] == StoneType.WHITE -> 0
            board[stone.position.y + up][stone.position.x + right] == StoneType.WHITE -> 0
            countToWall(board, stone, dx, dy) + countToWall(board, stone, dx, dy) <= 5 -> 0
            else -> 1
        }
    }

    private fun countToWall(
        board: List<List<StoneType>>,
        stone: Stone,
        dx: Int,
        dy: Int,
    ): Int {
        var (x, y) = stone.position
        var distance = 0
        while (willExceedBounds(x, y, dx, dy).not()) {
            x += dx
            y += dy
            when (board[y][x]) {
                in listOf(StoneType.BLACK, StoneType.EMPTY) -> distance++
                StoneType.WHITE -> break
                else -> throw IllegalArgumentException()
            }
        }
        return distance
    }

    private fun willExceedBounds(x: Int, y: Int, dx: Int, dy: Int): Boolean = when {
        dx > 0 && x == MAX_X -> true
        dx < 0 && x == MIN_X -> true
        dy > 0 && y == MAX_Y -> true
        dy < 0 && y == MIN_Y -> true
        else -> false
    }

    private fun checkOpenFour(
        board: List<List<StoneType>>,
        stone: Stone,
        dx: Int,
        dy: Int,
    ): Int {
        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

        val (x, y) = stone.position

        val leftDown = stone1 + blink1
        val left = dx * (leftDown + 1)
        val down = dy * (leftDown + 1)

        val rightUp = stone2 + blink2
        val right = dx * (rightUp + 1)
        val up = dy * (rightUp + 1)

        when {
            blink1 + blink2 == 2 && stone1 + stone2 == 4 -> return 2
            blink1 + blink2 == 2 && stone1 + stone2 == 5 -> return 2
            stone1 + stone2 != 3 -> return 0
            blink1 + blink2 == 2 -> return 0
        }

        val leftDownValid = when {
            dx != 0 && x - dx * leftDown in X_Edge -> 0
            dy != 0 && y - dy * leftDown in Y_Edge -> 0
            board[y - down][x - left] == StoneType.WHITE -> 0
            else -> 1
        }
        val rightUpValid = when {
            dx != 0 && x + (dx * rightUp) in X_Edge -> 0
            dy != 0 && y + (dy * rightUp) in Y_Edge -> 0
            board[y + up][x + right] == StoneType.WHITE -> 0
            else -> 1
        }

        return if (leftDownValid + rightUpValid >= 1) 1 else 0
    }

    private fun search(board: List<List<StoneType>>, stone: Stone, dx: Int, dy: Int): Pair<Int, Int> {
        var toRight = stone.position.x
        var toTop = stone.position.y
        var (stoneCount, blink) = initializeCounters()
        while (true) {
            if (!isWithinBounds(toRight, toTop, dx, dy)) break
            toRight += dx
            toTop += dy
            val (newStoneCount, newBlinkCount) = handleCurrentStoneType(board[toTop][toRight], stoneCount, blink)
            if (newBlinkCount != blink) blink = newBlinkCount
            if (isTerminatingStone(board[toTop][toRight])) break
            stoneCount = newStoneCount
        }
        return Pair(stoneCount, blink)
    }

    private fun initializeCounters(): Pair<Int, Int> = Pair(0, 0)

    private fun isWithinBounds(toRight: Int, toTop: Int, dx: Int, dy: Int): Boolean {
        return !(dx > 0 && toRight == MAX_X || dx < 0 && toRight == MIN_X || dy > 0 && toTop == MAX_Y || dy < 0 && toTop == MIN_X)
    }

    private fun handleCurrentStoneType(currentStoneType: StoneType, stoneCount: Int, blink: Int): Pair<Int, Int> {
        return when (currentStoneType) {
            StoneType.BLACK -> Pair(stoneCount + 1, blink)
            StoneType.WHITE -> Pair(stoneCount, blink)
            StoneType.EMPTY -> handleEmpty(currentStoneType, stoneCount, blink)
        }
    }

    private fun handleEmpty(currentStoneType: StoneType, stoneCount: Int, blink: Int): Pair<Int, Int> {
        return if (blink == 1) {
            Pair(stoneCount, blink)
        } else {
            Pair(stoneCount, if (stoneCount == 0) 1 else blink + 1)
        }
    }

    private fun isTerminatingStone(stoneType: StoneType): Boolean {
        return stoneType == StoneType.WHITE
    }

    *//*fun isWinCondition(board: List<List<StoneType>>, stone: Stone): Boolean {
        return when {
            checkHorizontal(board, stone) -> true
            checkVertical(board, stone) -> true
            checkDiagonal1(board, stone) -> true
            checkDiagonal2(board, stone) -> true
            else -> false
        }
    }

    private fun checkHorizontal(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (x + i !in 1..BOARD_SIZE) continue
            if (board[y][x + i] != stone.type) count = 0
            if (board[y][x + i] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

    private fun checkVertical(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (y + i !in 1..BOARD_SIZE) continue
            if (board[y + i][x] != stone.type) count = 0
            if (board[y + i][x] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

    private fun checkDiagonal1(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (x + i !in 1..BOARD_SIZE) continue
            if (y + i !in 1..BOARD_SIZE) continue
            if (board[y + i][x + i] != stone.type) count = 0
            if (board[y + i][x + i] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

    private fun checkDiagonal2(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (x - i !in 1..BOARD_SIZE) continue
            if (y + i !in 1..BOARD_SIZE) continue
            if (board[y + i][x - i] != stone.type) count = 0
            if (board[y + i][x - i] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    } *//*

    fun validateBlackWin(board: List<List<StoneType>>, stone: Stone): Boolean =
        directions.map { direction -> checkWhiteWin(board, stone, direction.first, direction.second) }.contains(true)

    private fun checkWhiteWin(board: List<List<StoneType>>, stone: Stone, dx: Int, dy: Int): Boolean {
        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 == 4 -> true
            else -> false
        }
    }

    fun validateWhiteWin(board: List<List<StoneType>>, stone: Stone): Boolean =
        directions.map { direction -> checkBlackWin(board, stone, direction.first, direction.second) }.contains(true)

    private fun checkBlackWin(board: List<List<StoneType>>, stone: Stone, dx: Int, dy: Int): Boolean {
        val (stone1, blink1) = search(board, stone, -dx, -dy)
        val (stone2, blink2) = search(board, stone, dx, dy)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 >= 4 -> true
            else -> false
        }
    }*/
}
