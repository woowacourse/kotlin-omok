package omok.model

import omok.model.entity.Stone
import omok.model.rule.Rule

class Board private constructor(val stones: Set<Stone>) {
    constructor() : this(setOf<Stone>())

    fun place(stone: Stone): PlaceStoneResult =
        when {
            !(stone.point.x in SIZE_RANGE && stone.point.y in SIZE_RANGE) -> StoneOutOfBoard()
            stones.find { it.point == stone.point } != null -> StoneAlreadyExists()
            else -> Success(Board(stones.plus(stone)))
        }

    fun previousStone(): Stone? {
        return stones.lastOrNull()
    }

    fun contains(stone: Stone): Boolean {
        return stones.contains(stone)
    }

    fun isFull(): Boolean = stones.count() == MAX_SIZE * MAX_SIZE

    fun check(rule: Rule): Boolean = rule.check(this)

    companion object {
        private const val MIN_SIZE = 1
        private const val MAX_SIZE = 15
        private val SIZE_RANGE = MIN_SIZE..MAX_SIZE
    }
}
