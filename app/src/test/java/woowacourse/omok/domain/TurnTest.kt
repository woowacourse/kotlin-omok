package woowacourse.omok.domain

import omok.domain.Turn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TurnTest {
    @Test
    fun `턴은 흑을 기본 색상으로 가진다`() {
        val turn = Turn()
        assertThat(turn.color).isEqualTo(StoneType.BLACK)
    }

    @Test
    fun `턴은 자신의 색상을 백으로 변경할 수 있다`() {
        val turn = Turn()
        turn.next()
        assertThat(turn.color).isEqualTo(StoneType.WHITE)
    }
}
