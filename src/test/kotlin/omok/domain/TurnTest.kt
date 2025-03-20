package omok.domain

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class TurnTest {
    @Test
    fun `턴은 돌을 생성한다`() {
        val turn = Turn()
        val blackStone = turn.stone("H10")
        assertThat(blackStone).isEqualTo(Stone(Position(7, 4), StoneType.BLACK))
    }
}