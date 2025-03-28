package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.grid.OmokGrid

class OmokGameTest {
    private lateinit var omokGame: OmokGame

    @BeforeEach
    fun setUp() {
        omokGame = OmokGame(OmokGrid())
    }

    @Test
    @DisplayName("흑돌 다음엔 백돌 차례이다")
    fun changeTurnWithBlackStone() {
        // when
        val actual = omokGame.changeTurn(StoneColor.BLACK)
        val expected = StoneColor.WHITE

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("백돌 다음엔 흑돌 차례이다")
    fun changeTurnWithWhiteStone() {
        // when
        val actual = omokGame.changeTurn(StoneColor.WHITE)
        val expected = StoneColor.BLACK

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
