package omok.model

import omok.fixture.BLACK_A_1
import omok.fixture.BLACK_A_2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `보드에 바둑돌을 놓으면,보드에 바둑돌이 추가된다`() {
        // given
        val board = Board()
        val newStone = Stone(Point(1, 2), Color.WHITE)
        // when
        board.add(newStone)
        // then
        val actual = board.stones.stones
        val expected = listOf<Stone>(newStone)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `다른 위치에 바둑돌을 놓을 수 있다`() {
        // given
        val stone = BLACK_A_1
        val board = Board()
        board.add(stone)

        val differentPositionStone = BLACK_A_2
        // when
        board.add(differentPositionStone)
        val actual = board.stones.stones.size
        val expected = 2
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
