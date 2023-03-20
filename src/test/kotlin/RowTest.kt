
import domain.stone.Row
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class RowTest {
    @Test
    fun `y좌표로 0에서 14를 벗어나는 값을 받으면 예외가 발생한다`() {
        assertAll(
            "y좌표로 0에서 14를 벗어나는 값을 받으면 예외가 발생한다",
            { assertThrows<IllegalArgumentException> { Row.valueOf(15) } },
            { assertThrows<IllegalArgumentException> { Row.valueOf(-1) } }
        )
    }
}
