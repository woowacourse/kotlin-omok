package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {
    @Test
    fun `돌을 추가할 수 있다`() {
        // given
        val player = Player(Color.BLACK, listOf(Position.of(1, 'A')))
        // when
        player.place(Position.of(2, 'B'))
        // then
        assertThat(player.positions).hasSize(2)
    }
}
