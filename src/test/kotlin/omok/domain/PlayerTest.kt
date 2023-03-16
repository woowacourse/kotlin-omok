package omok.domain

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {
    @Test
    fun `플레이어는 자신이 둔 돌을 갖고 있다`() {
        // given
        val player = Player()
        val stone = Stone(Position(HorizontalAxis.A, 4))

        // when
        player.put(stone)

        // then
        assertThat(player.hand.stones.size).isEqualTo(1)
    }
}
