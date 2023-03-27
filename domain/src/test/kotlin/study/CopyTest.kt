package study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CopyTest {

    class Board(val positions: MutableList<Int>)

    @Test
    fun `외부에서 지정한 값이 달라진 경우 내부에서도 달라지는가`() {
        // given
        val position = mutableListOf(1, 2, 3)
        val board = Board(position)

        // when
        position.add(4)
        position.add(5)

        // then
        assertThat(board.positions).isEqualTo(mutableListOf(1, 2, 3, 4, 5))
    }
}
