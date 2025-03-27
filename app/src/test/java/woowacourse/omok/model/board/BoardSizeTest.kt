package woowacourse.omok.model.board

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class BoardSizeTest {
    @Test
    fun `보드사이즈는 영문 알파벳의 개수보다 작아야 한다`() {
        val alphabets = 'A'..'Z'
        val alphabetsLength = alphabets.toList().size

        assertThrows<IllegalArgumentException> {
            BoardSize(alphabetsLength + 1)
        }
        assertDoesNotThrow {
            BoardSize(alphabetsLength)
        }
    }

    @Test
    fun `오목을 만들 수 있는 사이즈여야 한다`() {
        val omokLength = 5

        assertThrows<IllegalArgumentException> {
            BoardSize(omokLength - 1)
        }
        assertDoesNotThrow {
            BoardSize(omokLength)
        }
    }
}
