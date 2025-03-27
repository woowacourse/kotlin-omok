package omok.domain

import omok.library.FourFourRule
import omok.library.MoreThanFiveRule
import omok.library.ThreeThreeRule

class RenjuRule : Rule {
    private val fourFourRule = FourFourRule(BOARD_SIZE)
    private val threeThreeRule = ThreeThreeRule(BOARD_SIZE)
    private val moreThanFiveRule = MoreThanFiveRule(BOARD_SIZE)

    // 흑돌 체크
    override fun isValidMove(
        board: Board,
        position: Position,
        color: StoneType,
    ): Boolean {
        return when (color) {
            StoneType.BLACK -> checkBlackRestrictions(board, position)
            StoneType.WHITE -> true
            else -> throw IllegalArgumentException("유효하지 않은 돌 색상입니다.")
        }
    }

    // 우승 조건 체크
    override fun checkWin(
        board: Board,
        lastMove: Position,
        color: StoneType,
    ): Boolean {
        val grid = convertToLibraryFormat(board)
        return checkFiveInRow(grid, lastMove, color) &&
                checkNoOverline(grid, lastMove, color)
    }

    // 흑돌 금수 체크
    private fun checkBlackRestrictions(
        board: Board,
        position: Position,
    ): Boolean {
        val grid = convertToLibraryFormat(board)
        val posPair = position.toPair()
        return !(
                threeThreeRule.validate(grid, posPair) ||
                        fourFourRule.validate(grid, posPair) ||
                        moreThanFiveRule.validate(grid, posPair)
                )
    }

    // 5목 승리 조건
    private fun checkFiveInRow(
        grid: Array<Array<StoneType>>,
        pos: Position,
        color: StoneType,
    ): Boolean {
        directions.forEach { (dx, dy) ->
            val count =
                countDirection(grid, pos, dx, dy, color) +
                        countDirection(grid, pos, -dx, -dy, color) + 1
            if (count == 5) return true
        }
        return false
    }

    private fun checkNoOverline(
        grid: Array<Array<StoneType>>,
        pos: Position,
        color: StoneType,
    ): Boolean {
        directions.forEach { (dx, dy) ->
            val count =
                countDirection(grid, pos, dx, dy, color) +
                        countDirection(grid, pos, -dx, -dy, color) + 1
            if (count >= 6) return false
        }
        return true
    }

    private fun countDirection(
        grid: Array<Array<StoneType>>,
        start: Position,
        dx: Int,
        dy: Int,
        color: StoneType,
    ): Int {
        var count = 0
        var x = start.x + dx
        var y = start.y + dy

        while (x in 0 until BOARD_SIZE && y in 0 until BOARD_SIZE) {
            if (grid[x][y] == color) {
                count++
                x += dx
                y += dy
            } else {
                break
            }
        }
        return count
    }

    // 도메인 라이브러리 형식 변환
    private fun convertToLibraryFormat(board: Board): Array<Array<StoneType>> {
        return Array(BOARD_SIZE) { row ->
            Array(BOARD_SIZE) { col ->
                board.getStoneAt(Position(row, col))
            }
        }
    }

    private fun Position.toPair() = Pair(this.x, this.y)

    companion object {
        private const val BOARD_SIZE = 15
        private val directions =
            listOf(
                Pair(1, 0),
                Pair(0, 1),
                Pair(1, 1),
                Pair(1, -1),
            )
    }
}
