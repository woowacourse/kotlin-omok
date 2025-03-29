package woowacourse.omok.domain.model.rule.place

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.player.PlayerStone

@JvmInline
value class PlaceRules(
    private val value: List<PlaceRule> = emptyList(),
) : PlaceRule {
    override fun perform(
        board: OmokBoard,
        stone: PlayerStone,
    ): PlaceResult {
        value.forEach { rule ->
            val result = rule.perform(board, stone)
            if (result is PlaceResult.Failure) return result
        }
        return PlaceResult.Success
    }
}
