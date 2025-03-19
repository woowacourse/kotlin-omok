package omok.domain

import omok.controller.minus
import omok.util.retryInput
import rule.BlackRenjuRule
import rule.OmokRule
import rule.WhiteRenjuRule
import rule.wrapper.point.Point

class OmokGame {
    val grid: OmokGrid = OmokGrid()

    fun playGame(
        printBoardState: (List<MutableList<StoneState>>) -> Unit,
        getPoint: (StoneState, Point?) -> Point,
    ): OmokResult {
        var latestPoint: Point? = null
        var nowTurn: StoneState = StoneState.BLACK
        while (true) {
            latestPoint = turn(nowTurn, latestPoint, printBoardState, getPoint)
            if (checkOmok(latestPoint)) return OmokResult.returnWinner(nowTurn)
            if (grid.isFull()) break
            nowTurn = StoneState.changeTurn(nowTurn)
        }
        return OmokResult.DRAW
    }

    private fun turn(
        state: StoneState,
        latestPoint: Point?,
        printBoardState: (List<MutableList<StoneState>>) -> Unit,
        getPoint: (StoneState, Point?) -> Point,
    ): Point {
        return retryInput {
            printBoardState(grid.board)
            val point = getPoint(state, latestPoint).minus(1)
            validatePosition(state, point)
            grid.putStone(point, state)
            point
        }
    }

    private fun validatePosition(
        state: StoneState,
        point: Point,
    ) {
        if (grid.isViolation(getRule(state), point)) throw IllegalStateException(ERROR_WRONG_POSITION)
    }

    private fun getRule(state: StoneState): OmokRule {
        return if (state == StoneState.BLACK) {
            BlackRenjuRule()
        } else {
            WhiteRenjuRule()
        }
    }

    fun checkOmok(point: Point): Boolean {
        val directions: List<Direction> =
            listOf(
                Direction(0, 1),
                Direction(1, 0),
                Direction(1, 1),
                Direction(1, -1),
            )

        return directions.any { dir ->
            val count = search(dir, point) + search(-dir, point) - 1
            count >= OMOK_STANDARD
        }
    }

    private fun search(
        direction: Direction,
        point: Point,
    ): Int {
        val coordinateX = point.row
        val coordinateY = point.col
        val state = grid.board[coordinateX][coordinateY]
        var count = DEFAULT_COUNT

        while (checkRange(coordinateX + direction.rowDelta * count, coordinateY + direction.colDelta * count) &&
            grid.board[coordinateX + direction.rowDelta * count][coordinateY + direction.colDelta * count] == state
        ) {
            count++
        }

        return count
    }

    private fun checkRange(
        coordinateX: Int,
        coordinateY: Int,
    ): Boolean {
        return coordinateX in (MIN_BOUND..MAX_BOUND) && coordinateY in (MIN_BOUND..MAX_BOUND)
    }

    companion object {
        private const val DEFAULT_COUNT: Int = 0
        private const val OMOK_STANDARD: Int = 5
        const val MIN_BOUND = 0
        const val MAX_BOUND = 14

        private const val ERROR_WRONG_POSITION = "해당 위치에 놓을 수 없습니다"
    }
}
