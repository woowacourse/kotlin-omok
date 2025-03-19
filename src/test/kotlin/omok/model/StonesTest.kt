package omok.model

import omok.model.board.Position
import omok.model.board.Stones
import omok.model.board.X
import omok.model.board.Y
import omok.model.stone.BlackStone
import omok.model.stone.WhiteStone
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StonesTest {
    @Test
    fun `돌들의 좌표는 중복될 수 없다`() {
        assertThrows<IllegalArgumentException> {
            Stones(listOf(BlackStone(Position(X(1), Y(1))), WhiteStone(Position(X(1), Y(1)))))
        }
    }
}
