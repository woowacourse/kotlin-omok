package omok.model

import omok.fixture.FIRST_ROW_FIRST_COL
import omok.fixture.FIRST_ROW_SECOND_COL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
    fun `같은 위치에 바둑돌은 놓을 수 없다`() {
        // given
        val stone = FIRST_ROW_FIRST_COL
        val board = Board()
        board.add(stone)
        val samePositionStone = FIRST_ROW_FIRST_COL
        // when
        assertThrows<IllegalArgumentException> {
            board.add(samePositionStone)
        }
    }

    @Test
    fun `다른 위치에 바둑돌을 놓을 수 있다`() {
        // given
        val stone = FIRST_ROW_FIRST_COL
        val board = Board()
        board.add(stone)

        val differentPositionStone = FIRST_ROW_SECOND_COL
        // when
        board.add(differentPositionStone)
        val actual = board.stones.stones.size
        val expected = 2
        // then
        assertThat(actual).isEqualTo(expected)
    }
}
