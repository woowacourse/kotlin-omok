import omok.turn.BlackTurn
import omok.turn.Ready
import omok.turn.WhiteTurn
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TurnTest {
    @Test
    fun `게임을 시작할 때 흑이 먼저 돌을 둔다`() {
        val ready = Ready()
        val actual = ready.proceed()
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `오목판에 흑이 돌을 둔 다음 백이 두어야 한다`() {
        val whiteTurn = WhiteTurn()
        val actual = whiteTurn.proceed()
        Assertions.assertThat(actual).isInstanceOf(BlackTurn::class.java)
    }

    @Test
    fun `오목판에 백이 돌을 둔 다음 흑이 두어야 한다`() {
        val blackTurn = BlackTurn()
        val actual = blackTurn.proceed()
        Assertions.assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }
}
