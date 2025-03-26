package omok.domain.model.state

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import omok.domain.model.stone.StoneType
import org.junit.jupiter.api.Test

class OmokStateMachineTest {
    @Test
    fun `흑돌 차례일 때 돌을 두면 백돌 차례가 된다`() {
        // Given
        val omokStateMachine = OmokStateMachine()

        // When
        omokStateMachine.transition(OmokEvent.TURN)

        // Then
        omokStateMachine.state shouldBe WhiteStoneTurn
    }

    @Test
    fun `백돌 차례일 때 돌을 두면 흑돌 차례가 된다`() {
        // Given
        val omokStateMachine = OmokStateMachine()

        // When
        omokStateMachine.transition(OmokEvent.TURN)
        omokStateMachine.transition(OmokEvent.TURN)

        // Then
        omokStateMachine.state shouldBe BlackStoneTurn
    }

    @Test
    fun `승리하면 종료 상태가 된다`() {
        // Given
        val omokStateMachine = OmokStateMachine()

        // When
        omokStateMachine.transition(OmokEvent.WIN)

        // Then
        assertSoftly(omokStateMachine.state) {
            shouldBeTypeOf<Finish>()
            winner shouldBe StoneType.BLACK
        }
    }

    @Test
    fun `무승부면 종료 상태가 된다`() {
        // Given
        val omokStateMachine = OmokStateMachine()

        // When
        omokStateMachine.transition(OmokEvent.DRAW)

        // Then
        assertSoftly(omokStateMachine.state) {
            shouldBeTypeOf<Finish>()
            winner shouldBe StoneType.NONE
        }
    }
}
