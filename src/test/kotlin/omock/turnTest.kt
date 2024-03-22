package omock

import omock.model.turn.WhiteTurn
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class turnTest {
    @Test
    fun `백돌 플레이어는 원하는 좌표 돌을 뽑는다`() {
        val whiteStone =
            WhiteTurn().turn {
                Pair("A", "1")
            }

        Assertions.assertThat(whiteStone.column.comma).isEqualTo("A")
        Assertions.assertThat(whiteStone.row.comma).isEqualTo("1")
    }
}
