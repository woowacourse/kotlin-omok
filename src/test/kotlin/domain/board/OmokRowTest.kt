package domain.board

import omok.domain.board.OmokRow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmokRowTest {
    @Test
    fun `행의 라인 번호를 입력하면 행에 해당되는 번호를 반환한다`() {
        val result = OmokRow.find(1)

        assertEquals(result, OmokRow.ONE)
    }

    @Test
    fun `존재하지 않는 행의 라인 번호를 입력하면 에러를 반환한다`()  {
        assertThrows<IllegalArgumentException>(
            message = "잘못된 행 번호입니다. 다시 입력해주세요",
        ) {
            OmokRow.of("20")
        }
    }

    @Test
    fun `올바른 행의 라인 번호를 입력하면 오목 행을 반환한다`()  {
        val result = OmokRow.of("15")

        assertEquals(result, OmokRow.FIFTEEN)
    }
}
