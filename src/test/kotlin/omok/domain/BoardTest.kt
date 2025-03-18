package omok.domain

import omok.domain.state.WhiteTurn
import omok.fixture.A1
import omok.fixture.A3
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `마지막 돌의 위치를 구한다`() {
        val state =
            WhiteTurn(Stones(setOf(A1), StoneColor.BLACK), Stones(setOf(A3), StoneColor.WHITE))
        val board = Board(state)
        val expected = A1
        assertThat(board.lastStonePoint()).isEqualTo(expected)
    }
}
