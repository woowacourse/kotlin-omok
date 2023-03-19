import domain.stone.Column
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class ColumnTest {
    @Test
    fun `x좌표로 0에서 14를 벗어나는 값을 받으면 예외가 발생한다`() {
        assertAll(
            "x좌표로 0에서 14를 벗어나는 값을 받으면 예외가 발생한다",
            { assertThrows<IllegalArgumentException> { Column.valueOf(-1) } },
            { assertThrows<IllegalArgumentException> { Column.valueOf(15) } }
        )
    }
}
