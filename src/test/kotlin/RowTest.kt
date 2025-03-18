import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class RowTest {
    @Test
    fun `유효한 행이 아니라면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Row(0) }
    }

    @Test
    fun `행이 유효하면 생성한다`() {
        assertDoesNotThrow { Row(1) }
    }
}