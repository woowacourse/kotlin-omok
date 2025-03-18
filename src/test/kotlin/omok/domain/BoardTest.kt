package omok.domain

import omok.domain.state.WhiteTurn
import omok.fixture.A1
import omok.fixture.BLACK_A1
import omok.fixture.WHITE_A3
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `마지막 돌의 위치를 구한다`() {
        val state = WhiteTurn(Stones(listOf(BLACK_A1)), Stones(listOf(WHITE_A3)))
        val board = Board(state)
        val expected = A1
        assertThat(board.lastStonePoint()).isEqualTo(expected)
    }
}
