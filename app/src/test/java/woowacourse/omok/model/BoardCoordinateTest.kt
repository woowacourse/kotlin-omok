package omok.model

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class BoardCoordinateTest {
    @Test
    fun `유효한 값으로 CoordsNumber 객체 생성 성공`() {
        val validBoardCoordinate = BoardCoordinate.from(5)
        assertNotNull(validBoardCoordinate)
    }

    @Test
    fun `유효하지 않은 값으로 CoordsNumber 객체 생성 실패`() {
        val invalidBoardCoordinate = BoardCoordinate.from(15)
        assertNull(invalidBoardCoordinate)
    }
}
