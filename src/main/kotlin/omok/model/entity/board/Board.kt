package omok.model.entity.board

import omok.model.entity.Stone
import omok.model.entity.position.DefaultGridElement
import omok.model.entity.position.DefaultPosition
import omok.model.entity.position.GridElement
import omok.model.entity.position.Position

interface Board {
    val positions: Set<BoardPosition>
    val sideLength: GridElement

    fun put(
        position: Position,
        stone: Stone,
    )

    fun put(
        row: Int,
        column: Int,
        stone: Stone,
    )

    fun stateAt(position: Position): BoardPositionState

    fun stateAt(
        row: Int,
        column: Int,
    ): BoardPositionState
}

class DefaultBoard(
    positions: Set<BoardPosition>,
    override val sideLength: GridElement = DefaultGridElement(15),
) : Board {
    override var positions: Set<BoardPosition> = positions.toSet()
        private set

    constructor(position: BoardPosition) : this(setOf(position))

    override fun put(
        position: Position,
        stone: Stone,
    ) {
        val targetPosition: BoardPosition = boardPosition(position)
        positions -= targetPosition
        positions += targetPosition.withStone(stone)
    }

    override fun put(
        row: Int,
        column: Int,
        stone: Stone,
    ) {
        put(DefaultPosition(row, column), stone)
    }

    override fun stateAt(position: Position): BoardPositionState = boardPosition(position).state

    override fun stateAt(
        row: Int,
        column: Int,
    ): BoardPositionState = stateAt(DefaultPosition(row, column))

    private fun boardPosition(position: Position): BoardPosition =
        positions
            .find { boardPosition ->
                position == boardPosition.position
            } ?: throw IllegalArgumentException("$position 는 존재하지 않는 위치 입니다.")

    companion object {
        operator fun invoke(sideLength: GridElement): DefaultBoard {
            val defaultPositions: Set<BoardPosition> = defaultPositions(sideLength.value)
            return DefaultBoard(defaultPositions, sideLength)
        }

        operator fun invoke(sideLength: Int = 15): DefaultBoard {
            val defaultPositions: Set<BoardPosition> = defaultPositions(sideLength)
            return DefaultBoard(defaultPositions, DefaultGridElement(sideLength))
        }

        private fun defaultPositions(size: Int): Set<BoardPosition> =
            (0 until size)
                .flatMap { row: Int ->
                    (0 until size).map { column: Int ->
                        val position = DefaultPosition(row, column)
                        DefaultBoardPosition(position)
                    }
                }.toSet()
    }
}
