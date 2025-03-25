package omok.domain.model.state

import io.kotest.matchers.types.shouldBeTypeOf
import omok.adapter.RenjuRuleAdapter
import omok.domain.model.Board
import omok.domain.model.rule.OmokRule
import omok.domain.model.stone.StoneType
import omok.positionOneAndOne
import omok.verticalFourStones
import omok.verticalWinStone
import org.junit.jupiter.api.Test
import rule.BlackRenjuRule

class BlackStoneTurnTest {
    private val omokRule: OmokRule = OmokRule(RenjuRuleAdapter(BlackRenjuRule()))

    @Test
    fun `돌을 놓았을 때 오목이 되지 않으면 끝나지 않는다`() {
        // Given
        val board = Board()
        val state = Turn(board, omokRule, StoneType.BLACK)

        // When
        val newState = state.placeStone(positionOneAndOne)

        // Then
        newState.shouldBeTypeOf<Turn>()
    }

    @Test
    fun `흑돌을 놓았을 때 오목이 되면 게임이 종료된다`() {
        // Given
        val board = Board(verticalFourStones)
        val state = Turn(board, omokRule, StoneType.BLACK)

        // When
        val newState = state.placeStone(verticalWinStone.position)

        // Then
        newState.shouldBeTypeOf<Finish>()
    }
}
