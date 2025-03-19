package omok.model

import omok.model.board.Position
import omok.model.board.X
import omok.model.board.Y
import omok.model.stone.StoneColor
import omok.model.stone.WhiteStone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class WhiteStoneTest {
    @Test
    fun `흰돌은 흰색이다`() {
        val position = Position(X(1), Y(1))
        val actual = WhiteStone(position).color()

        Assertions.assertThat(actual).isEqualTo(StoneColor.WHITE)
    }

    @Test
    fun `흰돌은 좌표를 가진다`() {
        val position = Position(X(1), Y(1))
        val actual = WhiteStone(position).position()

        Assertions.assertThat(actual).isEqualTo(position)
    }
}
