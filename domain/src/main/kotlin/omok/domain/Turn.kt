package omok.domain

import omok.domain.player.Black
import omok.domain.player.Stone
import omok.domain.player.White

fun getTurn(counts: List<Int>): Turn {
    if (counts[Black.id] <= counts[White.id]) return Turn(setOf(Black, White), Black.id)
    return Turn(setOf(Black, White), White.id)
}

class Turn(private val stones: Set<Stone>, private var currentIndex: Int = 0) {
    val now: Stone
        get() = stones.elementAt(currentIndex)

    fun changeTurn() {
        currentIndex = if (now == stones.last()) 0 else currentIndex + 1
    }
}
