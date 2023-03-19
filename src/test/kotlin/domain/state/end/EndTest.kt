package domain.state.end

import domain.stone.StoneType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class EndTest {

    @ParameterizedTest
    @ValueSource(strings = ["WHITE", "BLACK"])
    fun `우승자의 StoneType을 반환한다 `(stoneType: StoneType) {
        val end = End(stoneType)
        assertThat(end.getWinner()).isEqualTo(stoneType)
    }
}
