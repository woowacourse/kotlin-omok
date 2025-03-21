package omok.domain.player

import omok.beforeDoubleFour
import omok.beforeDoubleThree
import omok.beforeOverLine
import omok.toViolation
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class BlackPlayerTest {
    @Test
    fun `흑돌이 삼삼일 때 렌주룰을 위반한다`() {
        // given
        val blackPlayer = BlackPlayer()
        val beforeDoubleThree = beforeDoubleThree()
        // when
        beforeDoubleThree.forEach { blackPlayer.addStone(it) }
        // then
        assertThatThrownBy {
            blackPlayer.validateRenjuRule(WhitePlayer().stones, toViolation())
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessage("3x3 위치에 놓을 수 없습니다")
    }

    @Test
    fun `흑돌이 사사일 때 렌주룰을 위반한다`() {
        // given
        val blackPlayer = BlackPlayer()
        val beforeDoubleFour = beforeDoubleFour()
        // when
        beforeDoubleFour.forEach { blackPlayer.addStone(it) }
        // then
        assertThatThrownBy {
            blackPlayer.validateRenjuRule(WhitePlayer().stones, toViolation())
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessage("4x4 위치에 놓을 수 없습니다")
    }

    @Test
    fun `흑돌이 장목일 때 렌주룰을 위반한다`() {
        // given
        val blackPlayer = BlackPlayer()
        val beforeOverLine = beforeOverLine()
        // when
        beforeOverLine.forEach { blackPlayer.addStone(it) }
        // then
        assertThatThrownBy {
            blackPlayer.validateRenjuRule(WhitePlayer().stones, toViolation())
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessage("장목 위치에 놓을 수 없습니다")
    }
}
