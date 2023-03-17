package omok.domain

import omok.domain.player.Stone

class Turn(private val stones: List<Stone>) {
    private var currentIndex = 0
    val now: Stone
        get() = stones[currentIndex]

    fun changeTurn() {
        currentIndex = if (currentIndex != stones.lastIndex) currentIndex + 1 else 0
    }
}
