import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `보드에 돌을 놓는다`() {
        val stone = Stone(1, 2)
        val board = Board()
        board.place(stone)
        val actual = board.contains(stone)
        Assertions.assertThat(actual).isTrue()
    }

    @Test
    fun `Stone이 좌표 범위 밖을 벗어났을 경우 예외를 표기한다`() {
        val stone = Stone(-1, -1)
        val board = Board()
        val actual = assertThrows<IllegalArgumentException> { board.place(stone) }.message
        Assertions.assertThat(actual).isEqualTo("보드 밖에 돌을 두었습니다.")
    }
}
