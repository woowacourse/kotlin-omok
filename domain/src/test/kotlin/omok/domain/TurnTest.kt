package omok.domain

import omok.domain.player.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TurnTest {

    @Test
    fun `블랙 다음 플레이어는 화이트이다`() {
        val turn = Turn(setOf(Stone.BLACK, Stone.WHITE))

        turn.changeTurn()

        assertThat(turn.now).isEqualTo(Stone.WHITE)
    }
}
