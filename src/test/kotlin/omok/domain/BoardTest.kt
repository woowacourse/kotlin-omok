package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class BoardTest {
    @Test
    fun `바둑판은 15개의 행을 가진 225칸이다`() {
        val board = Board()
        assertThat(board.grid.size).isEqualTo(15)
    }

    @Test
    fun `바둑판은 15개의 열을 가진 225칸이다`() {
        val board = Board()
        assertThat(board.grid[0].size).isEqualTo(15)
    }

    @Test
    fun `바둑판은 15개의 행과 15개의 열을 가진 225칸이다`() {
        val board = Board()
        assertThat(board.grid.flatten().size).isEqualTo(225)
    }
}