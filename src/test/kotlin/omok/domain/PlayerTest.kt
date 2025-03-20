package omok.domain

import omok.beforeDoubleFour
import omok.beforeDoubleThree
import omok.beforeOverLine
import omok.domain.player.BlackPlayer
import omok.domain.player.WhitePlayer
import omok.toViolation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerTest {
    @Test
    fun `삼삼일 때 렌주룰을 위반한다`() {
        // given
        val blackPlayer = BlackPlayer()
        val beforeDoubleThree = beforeDoubleThree()
        // when
        beforeDoubleThree.forEach { blackPlayer.addStone(it) }
        val actual = blackPlayer.isViolation(WhitePlayer().stones, toViolation())
        // then
        assertThat(actual).isTrue()
    }

    @Test
    fun `사사일 때 렌주룰을 위반한다`() {
        // given
        val blackPlayer = BlackPlayer()
        val beforeDoubleFour = beforeDoubleFour()
        // when
        beforeDoubleFour.forEach { blackPlayer.addStone(it) }
        val actual = blackPlayer.isViolation(WhitePlayer().stones, toViolation())
        // then
        assertThat(actual).isTrue()
    }

    @Test
    fun `장목일 때 렌주룰을 위반한다`() {
        // given
        val blackPlayer = BlackPlayer()
        val beforeOverLine = beforeOverLine()
        // when
        beforeOverLine.forEach { blackPlayer.addStone(it) }
        val actual = blackPlayer.isViolation(WhitePlayer().stones, toViolation())
        // then
        assertThat(actual).isTrue()
    }
}
