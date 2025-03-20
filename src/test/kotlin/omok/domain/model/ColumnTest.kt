package omok.domain.model

import omok.domain.model.position.Column
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ColumnTest {
    @ParameterizedTest
    @ValueSource(chars = ['P', 'Z', '1', '%'])
    fun `가로 범위가 A~O가 아닐 경우 예외 발생`(value: Char) {
        assertThatThrownBy { Column.from(value) }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("잘못된 위치입니다.")
    }
}
