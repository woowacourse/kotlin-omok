package omock

import omock.model.Column
import omock.model.Row
import omock.model.state.Black
import omock.model.state.Clear
import omock.model.state.Stone
import omock.model.state.White
import omock.model.turn.BlackTurn
import omock.model.turn.WhiteTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneStateTest {
    @Test
    fun `흑 플레이어가 UnPlaced 상태에서 put을하면 돌의 상태가 흑돌로 변한다`() {
        val unPlacedStone = Clear(Stone(Row("1"), Column("A")))
        assertThat(unPlacedStone.put(BlackTurn()).getOrThrow() is Black).isTrue()
    }

    @Test
    fun `백 플레이어가 UnPlaced 상태에서 put을하면 돌의 상태가 백돌로 변한다`() {
        val unPlacedStone = Clear(Stone(Row("1"), Column("A")))
        assertThat(unPlacedStone.put(WhiteTurn()).getOrThrow() is White).isTrue()
    }

    @Test
    fun `Placed 상태에서는 흑 플레이어가 put을 하면 실패가 발생한다`() {
        val placedStone = Black(Stone(Row("1"), Column("A")))
        assertThat(placedStone.put(BlackTurn()).isFailure).isTrue()
    }

    @Test
    fun `Placed 상태에서는 백 플레이어가  put을 하면 실패가 발생한다`() {
        val placedStone = White(Stone(Row("1"), Column("A")))
        assertThat(placedStone.put(WhiteTurn()).isFailure).isTrue()
    }
}
