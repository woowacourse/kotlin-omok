package omock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.omok.model.GameState
import woowacourse.omok.model.player.BlackPlayer
import woowacourse.omok.model.player.WhitePlayer
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stonestate.Black
import woowacourse.omok.model.stonestate.Clear
import woowacourse.omok.model.stonestate.White

class StoneStateTest {
    @Test
    fun `흑 플레이어가 UnPlaced 상태에서 put을하면 돌의 상태가 흑돌로 변한다`() {
        val unPlacedStone = Clear(Stone(Row("1"), Column("A")))
        assertThat((unPlacedStone.put(BlackPlayer()) as GameState.LoadStoneState.Success).stoneState is Black).isTrue()
    }

    @Test
    fun `백 플레이어가 UnPlaced 상태에서 put을하면 돌의 상태가 백돌로 변한다`() {
        val unPlacedStone = Clear(Stone(Row("1"), Column("A")))
        assertThat((unPlacedStone.put(WhitePlayer())  as GameState.LoadStoneState.Success).stoneState is White).isTrue()
    }

    @Test
    fun `Placed 상태에서는 흑 플레이어가 put을 하면 예외가 발생한다`() {
        val placedStone = Black(Stone(Row("1"), Column("A")))
        assertThrows<IllegalArgumentException> {
            placedStone.put(BlackPlayer())
        }
    }

    @Test
    fun `Placed 상태에서는 백 플레이어가  put을 하면 예외가 발생한다`() {
        val placedStone = White(Stone(Row("1"), Column("A")))
        assertThrows<IllegalArgumentException> {
            placedStone.put(WhitePlayer())
        }
    }
}
