package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneStateTest {
    @Test
    fun `현재 턴이 black일 때 다음 턴은 white다`() {
        val nowTurn = StoneState.BLACK

        val actual = StoneState.changeTurn(nowTurn)
        val expected = StoneState.WHITE

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `현재 턴이 white일 때 다음 턴은 black이다`() {
        val nowTurn = StoneState.WHITE

        val actual = StoneState.changeTurn(nowTurn)
        val expected = StoneState.BLACK

        assertThat(actual).isEqualTo(expected)
    }
}