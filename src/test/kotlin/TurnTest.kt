import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import player.Turn

class TurnTest {
    @CsvSource("WHITE, BLACK", "BLACK, WHITE")
    @ParameterizedTest
    fun `상대방 차례를 반환한다`(myTurn: Turn, expected: Turn) {
        val actual = myTurn.next()
        assertThat(actual).isEqualTo(expected)
    }
}
