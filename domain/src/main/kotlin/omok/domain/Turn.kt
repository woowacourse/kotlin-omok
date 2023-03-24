package omok.domain

import omok.domain.player.Stone

class Turn(private val stones: Set<Stone>) {
    private var currentIndex = 0
    val now: Stone
        get() = stones.elementAt(currentIndex)

    init {
        require(stones.isNotEmpty()) { "[ERROR] 돌이 1개이상 존재해야 합니다." }
    }

    fun changeTurn() {
        currentIndex = if (currentIndex == stones.size - 1) 0 else currentIndex + 1
    }
}
