package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhitePlayerTest {
    @Test
    fun `플레이어가 돌을 놓으면 입력받은 위치에 같은 색의 돌을 놓인다`() {
        // given
        val whitePlayer = WhitePlayer(Color.BLACK)
        val stones = Stones()

        // when
        whitePlayer.place(stones) {
            Coordinate.from(1, 2)!!
        }

        // then
        val except = Stone(Color.BLACK, Coordinate.from(1, 2)!!)
        assertThat(stones.value.last()).isEqualTo(except)
    }
}
