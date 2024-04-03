package woowacourse.omok.model.board

import woowacourse.omok.data.adapter.StonePosition
import woowacourse.omok.model.game.DuplicationPlace
import woowacourse.omok.model.game.PlaceType
import woowacourse.omok.model.player.Player

class Board(
    val size: Int,
    private var _board: MutableMap<Position, Stone> = initBoard(size),
) {
    val board: Map<Position, Stone>
        get() = _board.toMap()

    constructor(
        size: Int,
        stonePositions: List<StonePosition>,
    ) : this(
        size,
        initBoard(size).apply {
            stonePositions.forEach { this[it.position] = it.stone }
        },
    )

    fun place(
        position: Position,
        player: Player,
    ): PlaceType {
        if (find(position) != Stone.NONE) return DuplicationPlace
        return player.placeType(this, position).apply {
            if (canPlace()) _board[position] = player.stone
        }
    }

    fun find(position: Position): Stone {
        return _board[position] ?: throw IllegalArgumentException(INVALID_POSITION_MESSAGE)
    }

    fun emptyPosition(block: (Position) -> Boolean): Boolean {
        return _board.all {
            if (it.value == Stone.NONE) block(it.key) else true
        }
    }

    fun isFull(): Boolean {
        return _board.all { it.value != Stone.NONE }
    }

    fun clear() {
        _board = initBoard(size)
    }

    companion object {
        private const val INVALID_POSITION_MESSAGE = "올바르지 않은 위치입니다."

        private fun initBoard(size: Int) =
            (0 until size).flatMap { row ->
                (0 until size).map { col -> Position(row, col) }
            }.associateWith { Stone.NONE }
                .toMutableMap()
    }
}
