package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import rule.wrapper.point.Point

class StoneTest {

    @Test
    fun `자신의 위치와 스톤 타입을 가진다`() {
        val point = Point(1, 1)
        val stoneType: StoneType = StoneType.BLACK
        val stone: Stone = Stone.from(point, stoneType)

        assertAll(
            { assertThat(stone.point.row).isEqualTo(1) },
            { assertThat(stone.point.col).isEqualTo(1) },
            { assertThat(stone.type).isEqualTo(StoneType.BLACK) },
        )
    }
}
