package woowacourse.omok.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TurnTest {
    @Test
    fun `턴 전환 테스트`() {
        val turn = Turn()
        assertEquals(StoneType.BLACK, turn.current)

        turn.switch()
        assertEquals(StoneType.WHITE, turn.current)

        turn.switch()
        assertEquals(StoneType.BLACK, turn.current)
    }
}
