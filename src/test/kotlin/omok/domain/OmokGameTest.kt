package omok.domain

import omok.domain.grid.OmokGrid
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokGameTest {
    private lateinit var omokGame: OmokGame

    @BeforeEach
    fun setUp() {
        omokGame = OmokGame(OmokGrid())
    }

    @Test
    fun `흑돌부터 게임을 시작한다`() {
        // when
        val actual = omokGame.getStartingPlayer()
        val expected = StoneColor.BLACK

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흑돌 다음엔 백돌 차례이다`() {
        // when
        val actual = omokGame.changeTurn(StoneColor.BLACK)
        val expected = StoneColor.WHITE

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `백돌 다음엔 흑돌 차례이다`() {
        // when
        val actual = omokGame.changeTurn(StoneColor.WHITE)
        val expected = StoneColor.BLACK

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
