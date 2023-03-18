package omok.domain.board

import omok.domain.board.Row.Companion.toRow
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RowTest {
    @Test
    fun `8의 위쪽 로우는 9이다`() {
        val row = Row.EIGHT

        Assertions.assertThat(row.up()).isEqualTo(Row.NINE)
    }

    @Test
    fun `15의 위쪽 로우는 존재하지 않는다`() {
        val row = Row.FIFTEEN

        Assertions.assertThat(row.up()).isNull()
    }

    @Test
    fun `8의 아래쪽 로우는 7이다`() {
        val row = Row.EIGHT

        Assertions.assertThat(row.down()).isEqualTo(Row.SEVEN)
    }

    @Test
    fun `1의 아래쪽 로우는 존재하지 않는다`() {
        val row = Row.ONE

        Assertions.assertThat(row.down()).isNull()
    }

    @Test
    fun `입력으로 들어온 좌표값을 로우로 바꿔준다`() {
        val inputRow = 9

        assertThat(toRow(inputRow)).isEqualTo(Row.NINE)
    }
}
