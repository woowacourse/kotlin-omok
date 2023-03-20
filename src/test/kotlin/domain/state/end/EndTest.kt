package domain.state.end

import domain.stone.StonePosition
import domain.stone.StoneType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class EndTest {

    @ParameterizedTest
    @ValueSource(strings = ["WHITE", "BLACK"])
    fun `우승자의 StoneType을 반환한다 `(stoneType: StoneType) {
        val end = End(stoneType)
        assertThat(end.getWinner()).isEqualTo(stoneType)
    }

    @Test
    fun `End에서 next를 호출시 End를 반환한다`() {
        val end = End(StoneType.WHITE)
        val stonePosition: StonePosition = StonePosition.from(5, 5)!!
        assertThat(end.next(stonePosition) is End).isTrue
    }
}
