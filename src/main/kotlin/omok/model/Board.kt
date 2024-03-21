package omok.model

import omok.model.entity.Stone

class Board {
    private val _stones: MutableSet<Stone> = mutableSetOf()
    val stones: Set<Stone>
        get() = _stones.toSet()

    fun place(stone: Stone) {
        require(stone.point.x in SIZE_RANGE && stone.point.y in SIZE_RANGE) { "보드 밖에 돌을 두었습니다.돌을 놓은 곳 : ${stone.point.x} ${stone.point.y}" }
        require(_stones.find { it.point == stone.point } == null) { "돌이 이미 존재합니다. ${stone.point.x} ${stone.point.y}" }
        _stones.add(stone)
    }

    fun removeStone(stone: Stone) {
        _stones.remove(stone)
    }

    fun previousStone(): Stone? {
        return _stones.lastOrNull()
    }

    fun contains(stone: Stone): Boolean {
        return _stones.contains(stone)
    }

    fun isFull(): Boolean = _stones.count() == MAX_SIZE * MAX_SIZE

    companion object {
        private const val MIN_SIZE = 1
        private const val MAX_SIZE = 15
        private val SIZE_RANGE = MIN_SIZE..MAX_SIZE
    }
}
