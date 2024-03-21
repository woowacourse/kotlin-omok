package omok.model

import omok.model.board.ColumnNumber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ColumnNumberTest {
    @Test
    fun `문자로부터 컬럼 번호 변환 성공`() {
        val columnA = ColumnNumber.A.coordsNumber
        assertEquals(0, columnA.number)

        val columnO = ColumnNumber.O.coordsNumber
        assertEquals(14, columnO.number)
    }

    @Test
    fun `잘못된 문자로부터의 컬럼 번호 변환 실패`() {
        val invalidColumn = ColumnNumber.fromLetter('P')
        assertNull(invalidColumn)
    }
}
