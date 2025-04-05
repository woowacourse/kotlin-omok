package woowacourse.omok.model.board

import woowacourse.omok.model.Stone
import woowacourse.omok.model.adapter.RuleAdapter
import woowacourse.omok.model.adapter.RuleChecker
import woowacourse.omok.model.gameState.PutState
import woowacourse.omok.model.position.GridElement
import woowacourse.omok.model.position.Position

class Board(
    positions: Set<BoardCell>,
    val sideLength: GridElement = GridElement(15),
    private val ruleChecker: RuleChecker = RuleAdapter,
) {
    var positions: Set<BoardCell> = positions.toSet()
        private set

    constructor(position: BoardCell) : this(setOf(position))

    fun put(
        position: Position,
        stone: Stone,
    ): PutState {
        val targetPosition: BoardCell = getBoardCell(position)

        if (!targetPosition.isEmpty()) return PutState.ExistStone
        if (!ruleChecker.canPut(this, position, stone)) return PutState.ForbiddenStone

        updateBoard(position, stone)

        return PutState.CanPutStone
    }

    private fun updateBoard(
        targetPosition: Position,
        stone: Stone,
    ) {
        val targetBoardCell = getBoardCell(targetPosition)
        positions -= targetBoardCell
        positions += targetBoardCell.replace(stone)
    }

    fun put(
        row: Int,
        column: Int,
        stone: Stone,
    ): PutState = put(Position(row, column), stone)

    fun getBoardCell(position: Position): BoardCell =
        positions
            .find { boardPosition ->
                position == boardPosition.position
            } ?: throw IllegalArgumentException("$position 는 존재하지 않는 위치 입니다.")

    fun getBoardCell(
        row: Int,
        column: Int,
    ): BoardCell = getBoardCell(Position(row, column))

    fun isFull(): Boolean = positions.all { !it.isEmpty() }

    companion object {
        operator fun invoke(sideLength: Int = 15): Board {
            val defaultPositions: Set<BoardCell> = defaultPositions(sideLength)
            return Board(defaultPositions, GridElement(sideLength))
        }

        private fun defaultPositions(size: Int): Set<BoardCell> =
            (0 until size)
                .flatMap { row: Int ->
                    (0 until size).map { column: Int ->
                        val position = Position(row, column)
                        BoardCell.EmptyCell(position)
                    }
                }.toSet()
    }
}
