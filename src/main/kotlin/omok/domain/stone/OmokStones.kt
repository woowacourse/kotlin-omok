package omok.domain.stone

import omok.domain.Point
import omok.domain.rule.OmokRule
import omok.domain.rule.Violation

class OmokStones(
    private val rule: OmokRule,
    stones: Set<Stone> = emptySet(),
) {
    private val _stones = stones.toMutableSet()
    val stones = _stones.toSet()

    operator fun plus(stone: Stone): OmokStones = OmokStones(rule, _stones + stone)

    fun contains(point: Point): Boolean = point in _stones.map { it.point }.toSet()

    fun isOmok(lastStone: Stone): Boolean = rule.isOmok(_stones, lastStone)

    fun isFoul(lastStone: Stone): Boolean {
        val violation = rule.checkViolation(_stones, lastStone)
        return when (violation) {
            Violation.DOUBLE_THREE, Violation.DOUBLE_FOUR, Violation.OVERLINE -> true
            Violation.NONE -> false
        }
    }
}
