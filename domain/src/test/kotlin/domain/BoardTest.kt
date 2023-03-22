package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class BoardTest {
    @Test
    fun `턴을 흑백 순으로 반복한다`() {
        val players = Players()
        val stones = Stones()
        val omokRule = RenjuRule(stones)
        val board = Board(players, stones)
        board.repeatTurn(Coordinate.from(1, 1)!!, omokRule)
        board.repeatTurn(Coordinate.from(1, 2)!!, omokRule)
        assertAll({
            assertThat(stones.value[0].color).isEqualTo(Color.Black)
        }, {
            assertThat(stones.value[1].color).isEqualTo(Color.White)
        })
    }
}
