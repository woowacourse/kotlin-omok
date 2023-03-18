package domain.stone

import domain.state.Win
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class WinTest {

    @Test
    fun `우승자의 StoneType을 반환한다 `(stone: Stone) {
        val stonePosition = StonePosition.from(1, 1)
        val stoneType = StoneType.BLACK

        val end = Win(Stone(stonePosition!!, stoneType))
        Assertions.assertThat(end.getWinner()).isEqualTo(stoneType)
    }
}
