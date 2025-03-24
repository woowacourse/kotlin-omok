package omok.domain.model.state

import omok.domain.model.position.Position
import omok.domain.model.position.Stone
import omok.domain.model.rule.Rule
import omok.domain.model.stone.StoneType
import omok.domain.model.stone.Stones

class Turn(
    override val stones: Stones,
    private val rule: Rule,
    override val stoneType: StoneType,
) : OmokState {
    override fun placeStone(position: Position): OmokState {
        val stone = Stone(position, stoneType)
        if (rule.canPlace(stones, stone).not()) return this
        val addedStones = stones + stone
        if (rule.checkWin(addedStones, stone)) return Finish(stones, stoneType)
        return Turn(addedStones, rule, stoneType.reverse())
    }
}
