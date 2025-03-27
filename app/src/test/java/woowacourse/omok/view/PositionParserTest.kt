
package omok.view

import omok.domain.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PositionParserTest {
    @Test
    fun `A1`() {
        val actual = PositionParser.encode("A1")
        assertThat(actual).isEqualTo(Position(0, 0))
    }

    @Test
    fun `A15`() {
        val actual = PositionParser.encode("A15")
        assertThat(actual).isEqualTo(Position(0, 14))
    }

    @Test
    fun `O15`() {
        val actual = PositionParser.encode("O15")
        assertThat(actual).isEqualTo(Position(14, 14))
    }

    @Test
    fun `0x0`() {
        val actual = PositionParser.decode(Position(0, 0))
        assertThat(actual).isEqualTo("A1")
    }

    @Test
    fun `14x14`() {
        val actual = PositionParser.decode(Position(14, 14))
        assertThat(actual).isEqualTo("O15")
    }
}
