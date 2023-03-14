package model.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CoordinationTest {

    @ValueSource(ints = [1, 7, 15])
    @ParameterizedTest
    fun `좌표는 1이상 15이하이다`(number: Int) {
        // given

        // when
        val actual = Coordination.from(number)

        // then
        assertThat(actual is Coordination).isTrue
    }

    @ValueSource(ints = [-10, 0, 16])
    @ParameterizedTest
    fun `좌표가 1이상 15이하가 아니면 null 을 반환한다`(number: Int) {
        // given

        // when
        val actual = Coordination.from(number)

        // then
        assertThat(actual).isNull()
    }
}
