package omok.domain.rule

import omok.domain.stone.WhiteStones
import omok.fixture.C10
import omok.fixture.C11
import omok.fixture.C12
import omok.fixture.C13
import omok.fixture.C14
import omok.fixture.C15
import omok.fixture.D12
import omok.fixture.D13
import omok.fixture.D14
import omok.fixture.E12
import omok.fixture.E5
import omok.fixture.F6
import omok.fixture.G12
import omok.fixture.G7
import omok.fixture.H8
import omok.fixture.I9
import omok.fixture.J10
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenjuRuleTest {
    @Test
    fun `3-3이면 금수이다`() {
        val renjuRule = RenjuRule()
        val otherStones = WhiteStones(setOf())
        val targetPoint = D12
        val existingStones = setOf(C12, D13, D14, E12)
        assertThat(renjuRule.isFoul(otherStones, targetPoint, existingStones)).isTrue()
    }

    @Test
    fun `4-4이면 금수이다`() {
        val renjuRule = RenjuRule()
        val otherStones = WhiteStones(setOf())
        val targetPoint = C13
        val existingStones = setOf(C10, C11, C12, C14, C15, G12)
        assertThat(renjuRule.isFoul(otherStones, targetPoint, existingStones)).isTrue()
    }

    @Test
    fun `장목이면 금수이다`() {
        val renjuRule = RenjuRule()
        val otherStones = WhiteStones(setOf())
        val targetPoint = H8
        val existingStones = setOf(E5, F6, G7, I9, J10)
        assertThat(renjuRule.isFoul(otherStones, targetPoint, existingStones)).isTrue()
    }
}
