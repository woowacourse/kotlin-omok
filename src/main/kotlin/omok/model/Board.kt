package omok.model

import omok.model.entity.Stone

class Board {
    private val _stones: MutableSet<Stone> = mutableSetOf()
    val stones: Set<Stone>
        get() = _stones.toSet()

    fun place(stone: Stone) {
        require(stone.point.x in 1..15 && stone.point.y in 1..15) {
            "보드 밖에 돌을 두었습니다.돌을 놓은 곳 : ${stone.point.x} ${stone.point.y}"
        }
        require(_stones.contains(stone).not()) { "그 포인트에 이미 돌이 존재합니다." }
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

    fun isFull(): Boolean = _stones.count() == 15 * 15
}
