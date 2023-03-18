package omok.domain

import omok.domain.player.Stone

class Turn(private val stones: Set<Stone>) {
    private var currentIndex = 0
    val now: Stone
        get() = stones.elementAt(currentIndex)

    fun changeTurn() {
        currentIndex = if (now == stones.last()) 0 else currentIndex + 1
    }
}
