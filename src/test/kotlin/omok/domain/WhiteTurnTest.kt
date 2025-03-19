package omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WhiteTurnTest {
    @Test
    fun `턴은 백돌을 생성한다`() {
        val blackTurn = BlackTurn()
        val blackStone = blackTurn.stone("H10")
        assertThat(blackStone).isEqualTo(Stone(7, 4, StoneType.WHITE))
    }
}