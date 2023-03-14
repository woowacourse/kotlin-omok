package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class StonePositionTest {

    @Test
    fun `x좌표와 y좌표를 알고 있다`() {
        val stonePosition: StonePosition = StonePosition.from(x = 5, y = 7)
        assertAll(
            { assertThat(stonePosition.x).isEqualTo(5) },
            { assertThat(stonePosition.y).isEqualTo(7) },
        )
    }
}
