package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TurnTest {
    @Test
    fun `턴은 돌을 생성한다`() {
        val turn = Turn()
        val blackStone = turn.stone("H10")
        assertThat(blackStone).isEqualTo(Stone(Position.from(7, 9), StoneType.BLACK))
    }
}
