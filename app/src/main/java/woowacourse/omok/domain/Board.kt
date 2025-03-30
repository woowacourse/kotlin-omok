package woowacourse.omok.domain

import android.os.Build
import androidx.annotation.RequiresApi

class Board(stones: List<Stone>) {
    private val _stones: MutableList<Stone> = stones.toMutableList()
    val stones: List<Stone>
        get() = _stones.toList()

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun put(
        position: Position,
        stoneType: StoneType,
    ) {
        if (!isEmpty(position)) throw PositionOccupiedException(position)
        _stones.removeIf { it.position == position }
        _stones.addLast(Stone(position, stoneType))
    }

    private fun isEmpty(position: Position): Boolean {
        return _stones.first { it.position == position }.color == StoneType.EMPTY
    }

    companion object {
        fun initial(size: Int = 15): Board {
            val stones =
                (1..size)
                    .flatMap { x -> (1..size).map { y -> Position(x, y) } }
                    .map { Stone(it, StoneType.EMPTY) }
            return Board(stones)
        }
    }
}
