package omok.domain.player

import omok.beforeDoubleFour
import omok.beforeDoubleThree
import omok.beforeOverLine
import omok.toViolation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class WhitePlayerTest {
    @Test
    fun `백돌이 삼삼일 때 렌주룰을 위반하지 않는다`() {
        // given
        val whitePlayer = WhitePlayer()
        val beforeDoubleThree = beforeDoubleThree()
        // when
        beforeDoubleThree.forEach { whitePlayer.addStone(it) }
        // then
        assertDoesNotThrow {
            whitePlayer.isViolation(BlackPlayer().stones, toViolation())
        }
    }

    @Test
    fun `백돌이 사사일 때 렌주룰을 위반하지 않는다`() {
        // given
        val whitePlayer = WhitePlayer()
        val beforeDoubleFour = beforeDoubleFour()
        // when
        beforeDoubleFour.forEach { whitePlayer.addStone(it) }
        // then
        assertDoesNotThrow {
            whitePlayer.isViolation(BlackPlayer().stones, toViolation())
        }
    }

    @Test
    fun `백돌이 장목일 때 렌주룰을 위반하지 않는다`() {
        // given
        val whitePlayer = WhitePlayer()
        val beforeOverLine = beforeOverLine()
        // when
        beforeOverLine.forEach { whitePlayer.addStone(it) }
        // then
        assertDoesNotThrow {
            whitePlayer.isViolation(BlackPlayer().stones, toViolation())
        }
    }
}
