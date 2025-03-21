package omok.domain

import omok.domain.player.BlackPlayer
import omok.domain.player.WhitePlayer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokResultTest {
    @Test
    fun `마지막 플레이어가 black이면 black가 승리한다`() {
        // given
        val nowPlayer = BlackPlayer()
        // when
        val actual = OmokResult.getWinner(nowPlayer)
        val expected = OmokResult.BLACK_WIN
        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `마지막 플레이어가 white면 white가 승리한다`() {
        // given
        val nowPlayer = WhitePlayer()
        // when
        val actual = OmokResult.getWinner(nowPlayer)
        val expected = OmokResult.WHITE_WIN
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
