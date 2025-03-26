package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BoardTest {
    @Test
    fun `오목판은 15개의 행을 가진다`() {
        val board = Board()
        assertThat(board.grid.size).isEqualTo(15)
    }

    @Test
    fun `오목판은 15개의 열을 가진이다`() {
        val board = Board()
        assertThat(board.grid[0].size).isEqualTo(15)
    }

    @Test
    fun `오목판은 225칸이다`() {
        val board = Board()
        assertThat(board.grid.flatten().size).isEqualTo(225)
    }

    @Test
    fun `오목판은 오목돌을 원하는 위치에 놓는다`() {
        val board = Board()
        val stone = Stone(Position(8, 9), StoneType.BLACK)
        board.put(stone)
        val expected = StoneType.BLACK
        assertThat(board.grid[8][9]).isEqualTo(expected)
    }

    @ValueSource(ints = [-1, 15])
    @ParameterizedTest
    fun `오목판의 크기를 넘어가면 오목돌을 놓을 수 없다`(int: Int) {
        val board = Board()
        val stone = Stone(Position(int, int), StoneType.BLACK)
        assertThrows<IllegalArgumentException> { board.put(stone) }
    }
}
