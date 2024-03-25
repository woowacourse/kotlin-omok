package omok.model

import omok.fixture.FIRST_ROW_FIRST_COL
import omok.fixture.FIRST_ROW_SECOND_COL
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `놓고자 하는 stone의 위치에 이미 stone이 있는지 확인한다`() {
        val board = Board()
        val stones = Stones().also { it.add(Stone(1,1,Color.BLACK)) }
        board.addStones(stones)

        assertThrows<IllegalArgumentException> {
            board.checkDuplicate(Stone(1,1,Color.WHITE))
        }
    }
}
