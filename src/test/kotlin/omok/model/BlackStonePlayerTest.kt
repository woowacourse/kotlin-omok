package omok.model

import omok.fixture.FIRST_ROW_FIRST_COL
import omok.fixture.FIRST_ROW_SECOND_COL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackStonePlayerTest {
    @Test
    fun `BlackStonePlayer 는 Stone 을 추가할 수 있다`() {
        // when
        val blackStonePlayer = BlackStonePlayer()
        assertThat(blackStonePlayer.getStones().size).isEqualTo(0)

        // given
        blackStonePlayer.add(FIRST_ROW_SECOND_COL)

        // Then
        assertThat(blackStonePlayer.getStones().size).isEqualTo(1)
    }

    @Test
    fun `마지막 스톤을 알 수 있다`() {
        val blackStonePlayer = BlackStonePlayer()
        blackStonePlayer.add(FIRST_ROW_FIRST_COL)
        blackStonePlayer.add(FIRST_ROW_SECOND_COL)

        assertThat(blackStonePlayer.lastStone()).isEqualTo(FIRST_ROW_SECOND_COL)
    }
}
