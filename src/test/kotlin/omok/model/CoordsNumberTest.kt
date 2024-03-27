package omok.model

import omok.model.board.CoordsNumber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CoordsNumberTest {
    @Test
    fun `유효한 값으로 CoordsNumber 객체 생성 성공`() {
        val validCoordsNumber = CoordsNumber(5)
        assertEquals(5, validCoordsNumber.number)
    }

    @Test
    fun `유효하지 않은 값으로 CoordsNumber 객체 생성 실패`() {
        assertThrows<IllegalArgumentException> { CoordsNumber(15) }
    }
}
