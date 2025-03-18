package omok.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StonesTest {
    @Test
    fun `돌들의 좌표는 중복될 수 없다`() {
        assertThrows<IllegalArgumentException> {
            Stones(listOf(BlackStone(Position(X("A"), Y(1))), WhiteStone(Position(X("A"), Y(1)))))
        }
    }
}
