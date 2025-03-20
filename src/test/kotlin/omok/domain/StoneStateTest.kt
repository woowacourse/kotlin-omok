package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneStateTest {
    @Test
    fun `현재 턴이 black일 때 다음 턴은 white다`() {
        // given
        val nowTurn = StoneState.BLACK
        // when
        val actual = StoneState.changeTurn(nowTurn)
        val expected = StoneState.WHITE
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현재 턴이 white일 때 다음 턴은 black이다`() {
        // given
        val nowTurn = StoneState.WHITE
        // when
        val actual = StoneState.changeTurn(nowTurn)
        val expected = StoneState.BLACK
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
