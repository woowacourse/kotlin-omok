package woowacourse.omok.domain.model

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.omok.domain.model.position.Column

class ColumnTest {
    private val board = Board(15)

    @ParameterizedTest
    @ValueSource(ints = [0, -1, 21])
    fun `보드 사이즈에 포함되지 않을 경우 예외 발생`(value: Int) {
        assertThatThrownBy {
            Column.from(
                value,
                board.column,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("잘못된 위치입니다.")
    }
}
