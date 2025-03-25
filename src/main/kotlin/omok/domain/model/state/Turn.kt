package omok.domain.model.state

import omok.domain.model.position.Position
import omok.domain.model.position.Stone
import omok.domain.model.rule.PlaceResult
import omok.domain.model.rule.Rule
import omok.domain.model.stone.StoneType
import omok.domain.model.stone.Stones

class Turn(
    override val stones: Stones,
    private val rule: Rule,
    override val stoneType: StoneType,
) : OmokState {
    override fun placeStone(
        position: Position,
        onPlaceMessage: (String) -> Unit,
    ): OmokState {
        val stone = Stone(position, stoneType)
        when (val placeResult = rule.canPlace(stones, stone)) {
            is PlaceResult.DuplicatePosition -> {
                onPlaceMessage(placeResult.message)
                return this
            }

            is PlaceResult.RenJuRule -> {
                onPlaceMessage(placeResult.message)
                return this
            }

            is PlaceResult.OnPlace -> return checkWin(stones + stone, stone)
        }
    }

    private fun checkWin(
        stones: Stones,
        stone: Stone,
    ) = if (rule.checkWin(stones, stone)) {
        Finish(stones, stoneType)
    } else {
        Turn(stones, rule, stoneType.reverse())
    }
}
