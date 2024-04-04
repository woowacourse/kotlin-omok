package omok.model

import omok.fixture.FIRST_ROW_FIRST_COL
import omok.fixture.FIRST_ROW_SECOND_COL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlackStonePlayerTest {
    @Test
    fun `BlackStonePlayer 는 Stone 을 추가할 수 있다`() {
        // when
        val blackStonePlayer = BlackStonePlayer(Stones())
        assertThat(blackStonePlayer.stones().size).isEqualTo(0)

        // given
        blackStonePlayer.add(FIRST_ROW_SECOND_COL)

        // Then
        assertThat(blackStonePlayer.stones().size).isEqualTo(1)
    }

    @Test
    fun `마지막 스톤을 알 수 있다`() {
        val blackStonesPlayer = BlackStonePlayer(Stones())
        blackStonesPlayer.add(FIRST_ROW_FIRST_COL)
        blackStonesPlayer.add(FIRST_ROW_SECOND_COL)

        assertThat(blackStonesPlayer.requireLastStone()).isEqualTo(Stone(FIRST_ROW_SECOND_COL, Color.BLACK))
    }
}
