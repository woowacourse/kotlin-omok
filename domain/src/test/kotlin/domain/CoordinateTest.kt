package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CoordinateTest {

    @Test
    fun `좌표 생성시 15x15 오목판 안에서 생성하면 좌표 객체를 생성한다`() {
        // given
        val coordinate = Coordinate.from(14, 14)

        // when

        // then
        assertThat(coordinate).isNotNull
    }
}
