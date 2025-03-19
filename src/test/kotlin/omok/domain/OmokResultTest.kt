package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokResultTest {
    @Test
    fun `마지막 턴이 black이면 black가 승리한다`() {
        val nowTurn = StoneState.BLACK

        val actual = OmokResult.returnWinner(nowTurn)
        val expected = OmokResult.BLACKWIN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막 턴이 white면 white가 승리한다`() {
        val nowTurn = StoneState.WHITE

        val actual = OmokResult.returnWinner(nowTurn)
        val expected = OmokResult.WHITEWIN

        assertThat(actual).isEqualTo(expected)
    }
}
