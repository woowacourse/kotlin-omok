package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TurnTest {
    @Test
    fun `블랙의 다음 턴은 화이트이다`() {
        val turn = Turn(Color.BLACK)
        turn.next()
        assertThat(turn.isWhite()).isTrue
    }

    @Test
    fun `화이트의 다음 턴은 블랙이다`() {
        val turn = Turn(Color.WHITE)
        turn.next()
        assertThat(turn.isBlack()).isTrue
    }
}
