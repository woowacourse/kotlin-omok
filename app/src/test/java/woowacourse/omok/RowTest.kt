import woowacourse.omok.model.Row
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class RowTest {
    @Test
    fun `유효한 행이 아니라면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Row.from(0) }
    }

    @Test
    fun `행이 유효하면 생성한다`() {
        assertDoesNotThrow { Row.from(1) }
    }

    @Test
    fun `같은 행인지 판단한다`() {
        val row = Row.from(1)
        val sameRow = Row.from(1)
        val otherRow = Row.from(2)

        assertAll(
            { assertTrue(row == sameRow) },
            { assertFalse(row == otherRow) },
        )
    }
}
