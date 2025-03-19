package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokResultTest {
    @Test
    fun `마지막 플레이어가 black이면 black가 승리한다`() {
        val nowPlayer = BlackPlayer()

        val actual = OmokResult.returnWinner(nowPlayer)
        val expected = OmokResult.BLACKWIN

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막 플레이어가 white면 white가 승리한다`() {
        val nowPlayer = WhitePlayer()

        val actual = OmokResult.returnWinner(nowPlayer)
        val expected = OmokResult.WHITEWIN

        assertThat(actual).isEqualTo(expected)
    }
}
