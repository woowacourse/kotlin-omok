package domain.stone

import domain.state.End
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EndTest {

    @Test
    fun `게임이 끝난 상태일 때 바둑알을 놓으면 End 반환`() {
        val stonePosition = StonePosition.from(1, 1)
        val stoneType = StoneType.BLACK
        val stone = Stone(stonePosition, stoneType)

        val end = End(stone)
        assertThat(end.put(stone) is End).isTrue()
    }
}
