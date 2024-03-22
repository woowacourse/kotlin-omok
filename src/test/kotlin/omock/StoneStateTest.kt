package omock

import omock.model.Black
import omock.model.Clear
import omock.model.Column
import omock.model.Row
import omock.model.Stone
import omock.model.White
import omock.model.turn.BlackTurn
import omock.model.turn.WhiteTurn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StoneStateTest {
    @Test
    fun `흑 플레이어가 UnPlaced 상태에서 put을하면 돌의 상태가 흑돌로 변한다`() {
        val unPlacedStone = Clear(Stone(Row("1"), Column("A")))
        assertThat(unPlacedStone.put(BlackTurn()) is Black).isTrue()
    }

    @Test
    fun `백 플레이어가 UnPlaced 상태에서 put을하면 돌의 상태가 백돌로 변한다`() {
        val unPlacedStone = Clear(Stone(Row("1"), Column("A")))
        assertThat(unPlacedStone.put(WhiteTurn()) is White).isTrue()
    }

    @Test
    fun `Placed 상태에서는 흑 플레이어가 put을 하면 예외가 발생한다`() {
        val placedStone = Black(Stone(Row("1"), Column("A")))
        assertThrows<IllegalArgumentException> {
            placedStone.put(BlackTurn())
        }
    }

    @Test
    fun `Placed 상태에서는 백 플레이어가  put을 하면 예외가 발생한다`() {
        val placedStone = White(Stone(Row("1"), Column("A")))
        assertThrows<IllegalArgumentException> {
            placedStone.put(WhiteTurn())
        }
    }
}
