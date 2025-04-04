import woowacourse.omok.model.Stone
import woowacourse.omok.model.StoneColor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class StoneTest {
    @Test
    fun `오목돌을 생성한다`() {
        assertDoesNotThrow {
            Stone.ofOrNull("A1", StoneColor.BLACK)
        }
    }
}
