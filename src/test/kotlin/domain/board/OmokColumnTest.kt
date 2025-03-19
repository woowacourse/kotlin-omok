package domain.board

import omok.domain.board.OmokColumn
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmokColumnTest {
    @Test
    fun `열의 라인 번호를 입력하면 열에 해당되는 알파벳을 반환한다`() {
        val result = OmokColumn.find(1)

        assertEquals(result, OmokColumn.A)
    }

    @Test
    fun `존재하지 않는 열의 알파벳을 입력하면 에러를 반환한다`() {
        assertThrows<IllegalArgumentException>(
            message = "잘못된 좌표 알파벳입니다. 다시 입력해주세요",
        ) {
            OmokColumn.of("Z")
        }
    }

    @Test
    fun `올바른 열의 알파벳을 입력하면 해당하는 오목 열을 반환한다`() {
        val result = OmokColumn.of("A")
        assertEquals(result, OmokColumn.A)
    }
}
