package omok.domain.model

import omok.domain.model.position.Row
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class RowTest {
    @ParameterizedTest
    @ValueSource(ints = [0, -15, 16])
    fun `위치가 1~15가 아닐 경우 예외 발생`(value: Int) {
        assertThatThrownBy { Row(value) }.isInstanceOf(IllegalArgumentException::class.java).hasMessage("잘못된 위치입니다.")
    }
}
