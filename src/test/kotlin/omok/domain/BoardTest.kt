package omok.domain

import omok.A1
import omok.Full
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board(RenjuRuleAdapter())
    }

    @Test
    fun `오목판은 15개의 행을 가진다`() {
        assertThat(board.grid.size).isEqualTo(15)
    }

    @Test
    fun `오목판은 15개의 열을 가진이다`() {
        assertThat(board.grid[0].size).isEqualTo(15)
    }

    @Test
    fun `오목판은 225칸이다`() {
        assertThat(board.grid.flatten().size).isEqualTo(225)
    }

    @Test
    fun `오목판은 오목돌을 원하는 위치에 놓는다`() {
        board.put(Position.from(8, 9), StoneType.BLACK)
        val expected = StoneType.BLACK
        assertThat(board.grid[8][9]).isEqualTo(expected)
    }

    @Test
    fun `오목판이 다 채워지면 더 이상 놓을 수 없다1`() {
        Full.forEach { board.put(it.position, it.color) }
        assertThat(board.isFull()).isTrue()
    }

    @Test
    fun `오목판이 다 채워지면 더 이상 놓을 수 없다2`() {
        Full.forEach { board.put(it.position, it.color) }
        assertThrows<IllegalArgumentException> { board.put(A1, StoneType.BLACK) }
    }
}
