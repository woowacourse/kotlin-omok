package domain.stone

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class StoneTest {
    @Test
    fun `x좌표와 y좌표로 바둑알을 생성할 수 있다`() {
        assertDoesNotThrow {
            Stone(XCoordinate.of('A'), YCoordinate.of(1))
        }
    }
}
