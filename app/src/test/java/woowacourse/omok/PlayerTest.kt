package omock

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import woowacourse.omok.model.GameState
import woowacourse.omok.model.player.WhitePlayer

class PlayerTest {
    @Test
    fun `백돌 플레이어는 원하는 좌표 돌을 뽑는다`() {
        val whiteStoneResult =
            WhitePlayer()
                .turn { Pair("A", "1") }
        val whiteStone = (whiteStoneResult as GameState.LoadStone.Success).stone
        Assertions.assertThat(whiteStone.getColumnComma()).isEqualTo("A")
        Assertions.assertThat(whiteStone.getRowComma()).isEqualTo("1")
    }
}
