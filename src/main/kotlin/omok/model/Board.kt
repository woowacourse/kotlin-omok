package omok.model

import PlaceStoneInterrupt
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.rule.Rule

class Board private constructor(private val _stones: Set<Stone>) {
    constructor() : this(setOf<Stone>())

    val stones: Set<Stone>
        get() = _stones.toSet()

    fun place(stone: Stone): Either<PlaceStoneInterrupt, Board> =
        when {
            !isPointInBoard(stone.point) -> Either.Left(PlaceStoneInterrupt.StoneOutOfBoard())
            !isPointEmpty(stone.point) -> Either.Left(PlaceStoneInterrupt.StoneAlreadyExists())
            else -> Either.Right(Board(_stones + stone))
        }

    fun checkRulesAny(rules: List<Rule>) =
        rules.any {
            it.check(this)
        }

    fun previousStone(): Stone? {
        return _stones.lastOrNull()
    }

    fun contains(stone: Stone): Boolean {
        return _stones.contains(stone)
    }

    fun isPointInBoard(point: Point): Boolean = (point.x in SIZE_RANGE && point.y in SIZE_RANGE)

    fun isPointEmpty(point: Point): Boolean = _stones.find { it.point == point } == null

    fun isFull(): Boolean = _stones.count() == MAX_SIZE * MAX_SIZE

    companion object {
        const val MIN_SIZE = 1
        const val MAX_SIZE = 15
        val SIZE_RANGE = MIN_SIZE..MAX_SIZE
    }
}
