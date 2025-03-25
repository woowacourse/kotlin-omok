package omok.domain.model.state

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import omok.domain.model.Board
import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.rule.OmokRuleAdapter
import omok.domain.model.stone.StoneType
import omok.doubleThreeFixture
import omok.horizontalWinStones
import org.junit.jupiter.api.Test

class OmokStateMachineTest {
    private val omokRuleAdapter = OmokRuleAdapter()

    @Test
    fun `흑돌 차례일 때 돌을 두면 백돌 차례가 된다`() {
        // Given
        val omokStateMachine = OmokStateMachine(rule = omokRuleAdapter)

        // When
        val result = omokStateMachine.placeStone { Position(Column.from('A'), Row(1)) }

        // Then
        result.state.shouldBeTypeOf<WhiteStoneTurn>()
    }

    @Test
    fun `백돌 차례일 때 돌을 두면 흑돌 차례가 된다`() {
        // Given
        val omokStateMachine = OmokStateMachine(state = WhiteStoneTurn, rule = omokRuleAdapter)

        // When
        val result = omokStateMachine.placeStone { Position(Column.from('A'), Row(1)) }

        // Then
        result.state.shouldBeTypeOf<BlackStoneTurn>()
    }

    @Test
    fun `돌을 두었을 때 오목이 되면 우승자를 반환한다`() {
        // Given
        val board = Board(stones = horizontalWinStones)
        val omokStateMachine = OmokStateMachine(board = board, rule = omokRuleAdapter)

        // When
        val result = omokStateMachine.placeStone { Position(Column.from('E'), Row(1)) }

        // Then
        assertSoftly(result.state) {
            shouldBeTypeOf<Finish>()
            winner shouldBe StoneType.BLACK
        }
    }

    @Test
    fun `금수를 두면 예외가 발생한다`() {
        // Given
        val board = Board(stones = doubleThreeFixture)
        val omokStateMachine = OmokStateMachine(board = board, rule = omokRuleAdapter)

        // Then
        shouldThrowExactly<IllegalArgumentException> {
            omokStateMachine.placeStone { Position(Column.from('E'), Row(3)) }
        }.message shouldBe "해당 위치에는 돌을 놓을 수 없습니다."
    }
}
