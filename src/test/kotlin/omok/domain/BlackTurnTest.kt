package omok.domain

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class BlackTurnTest {
    @Test
    fun `턴은 흑돌을 생성한다`() {
        val blackTurn = BlackTurn()
        val blackStone = blackTurn.stone("H10")
        assertThat(blackStone).isEqualTo(Stone(7, 4, StoneType.BLACK))
    }
}