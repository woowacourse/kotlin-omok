package domain.stone

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class StoneTest {

    @Test
    fun `자신의 위치와 스톤 타입을 가진다`() {
        val stonePosition: StonePosition = StonePosition(1, 1)
        val stoneType: StoneType = StoneType.BLACK
        val stone: Stone = Stone(stonePosition, stoneType)

        assertAll(
            { assertThat(stone.position.x).isEqualTo(1) },
            { assertThat(stone.position.y).isEqualTo(1) },
            { assertThat(stone.type).isEqualTo(StoneType.BLACK) },
        )
    }
}
