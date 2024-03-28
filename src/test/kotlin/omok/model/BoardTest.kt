package omok.model

import omok.fixture.FIRST_ROW_FIRST_COL
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `놓고자 하는 stone의 위치에 이미 stone이 있는지 확인한다`() {
        val board = Board().also { it.add(Stone(1, 1, Color.BLACK)) }

        assertThrows<IllegalArgumentException> {
            board.add(Stone(1, 1, Color.WHITE))
        }
    }

    @Test
    fun `stone 을 하나 추가할 경우, Stones 의 사이즈는 1 증가한다`() {
        // when
        val board = Board()
        Assertions.assertThat(board.stones.size).isEqualTo(0)

        // given
        board.add(Stone(FIRST_ROW_FIRST_COL, Color.BLACK))

        // then
        Assertions.assertThat(board.stones.size).isEqualTo(1)
    }
}
