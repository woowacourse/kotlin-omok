package woowacourse.omok.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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

    @Test
    fun `돌 배치 검증`() {
        val board = Board.initial()
        board.put(Position(3, 3), StoneType.BLACK)
        assertTrue(board.stones.any { it.position == Position(3, 3) })
    }
}
