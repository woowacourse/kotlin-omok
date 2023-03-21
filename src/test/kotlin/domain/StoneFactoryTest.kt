package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneFactoryTest {
    @Test
    fun `돌과 좌표가 주어지면 주어진 돌과 같은 색상으로 주어진 좌표의 돌을 생성한다`() {
        val stone = StoneFactory.createSameColorStone(BlackStone('A', 1), Point('C', 2))
        assertThat(stone).isEqualTo(BlackStone('C', 2))
    }
}