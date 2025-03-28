package woowacourse.omok.domain

import android.os.Build
import androidx.annotation.RequiresApi

class Board(stones: List<Stone>) {
    private val _stones: MutableList<Stone> = stones.toMutableList()
    val stones: List<Stone>
        get() = _stones.toList()

    val lastTurn: StoneType
        get() =
            _stones
                .lastOrNull()
                ?.color
                ?: StoneType.EMPTY

    val currentTurn: StoneType
        get() =
            when (lastTurn) {
                StoneType.BLACK -> StoneType.WHITE
                StoneType.WHITE -> StoneType.BLACK
                StoneType.EMPTY -> StoneType.BLACK
            }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun put(
        position: Position,
        stoneType: StoneType,
    ) {
        if (stoneType == lastTurn) throw NotYourTurnException()
        if (!isEmpty(position)) throw DuplicatePutException()
        _stones.removeIf { it.position == position }
        _stones.addLast(Stone(position, stoneType))
    }

    private fun isEmpty(position: Position): Boolean {
        return _stones.first { it.position == position }.color == StoneType.EMPTY
    }

    companion object {
        fun initial(): Board {
            val stones =
                (1..15)
                    .flatMap { x -> (1..15).map { y -> Position(x, y) } }
                    .map { Stone(it, StoneType.EMPTY) }
            return Board(stones)
        }
    }
}
