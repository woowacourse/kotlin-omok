package domain

import domain.stone.YCoordinate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class YCoordinateTest {

    @Test
    fun `int 타입의 숫자로 Y 좌표를 생성할 수 있다`() {
        assertDoesNotThrow { YCoordinate.of(15) }
    }
}
