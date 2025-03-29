package woowacourse.omok.domain.model.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.stone.StoneType

class TurnTest {
    private lateinit var turn: Turn

    @BeforeEach
    fun setUp() {
        turn = Turn(StoneType.BLACK)
    }

    @Test
    fun `게임이 끝나는 상태를 출력하는 기능 구현`() {
        assertThat(turn.finish()).isInstanceOf(Finish::class.java)
    }

    @Test
    fun `턴을 호출할 경우 stone이 변경된다`() {
        assertThat(turn.turn().stoneType).isEqualTo(StoneType.WHITE)
    }

    @Test
    fun `턴은 끝난 상태가 아니다`() {
        assertThat(turn.isFinished()).isFalse()
    }
}
