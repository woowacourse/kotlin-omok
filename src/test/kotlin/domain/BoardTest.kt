package domain

import domain.Color.BLACK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BoardTest {
    @Test
    fun `차례를 반환한다`() {
        val board = Board()
        val color: Color = board.turn

        assertThat(color).isEqualTo(BLACK)
    }
}
