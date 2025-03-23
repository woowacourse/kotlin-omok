package omok.model.rule

import omok.mapper.BlackRuleChecker
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position

class BlackOmokRule(
    private val blackRuleChecker: BlackRuleChecker,
) : Rule {
    override fun isWin(
        stones: Map<Position, StoneColor>,
        lastStone: Stone,
    ): Boolean {
        val positions = positions(stones)

        return blackRuleChecker.checkWin(positions.first, positions.second, lastStone.position)
    }

    override fun validate(
        stones: Map<Position, StoneColor>,
        nextPosition: Position,
        color: StoneColor,
    ) {
        if (color != StoneColor.BLACK) return

        val positions = positions(stones)
        blackRuleChecker.checkFoul(positions.first, positions.second, nextPosition)
    }

    private fun positions(stones: Map<Position, StoneColor>): Pair<List<Position>, List<Position>> {
        val blackPoints = stones.filter { it.value == StoneColor.BLACK }.map { it.key }
        val whitePoints = stones.filter { it.value == StoneColor.WHITE }.map { it.key }

        return (blackPoints to whitePoints)
    }
}
