import woowacourse.omok.model.Col
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class ColTest {
    @Test
    fun `유효한 열이 아니라면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Col.from('Z') }
    }

    @Test
    fun `열이 유효하면 생성한다`() {
        assertDoesNotThrow { Col.from('A') }
    }

    @Test
    fun `A를 1로 계산한다`() {
        // given+when
        val col = Col.from('A')
        // result
        assertEquals(col.value, 1)
    }

    @Test
    fun `같은 열인지 판단한다`() {
        // given
        val col = Col.from('A')
        // when
        val sameCol = Col.from('A')
        val otherCol = Col.from('B')
        // result
        assertAll(
            { assertTrue(col == sameCol) },
            { assertFalse(col == otherCol) },
        )
    }
}
