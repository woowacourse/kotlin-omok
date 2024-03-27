package omok.model.stone

import omok.model.board.Board
import omok.model.position.Position
import omok.model.rule.X_A
import omok.model.rule.Y_1
import omok.model.rule.Y_2
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class BlackStoneTest {
    @BeforeEach
    fun setUp() {
        Board.reset()
    }

    @Test
    fun `이미 돌이 놓인 자리에 돌을 놓는다면 예외가 발생한다`() {
        val stone = BlackStone()
        val position = Position(X_A, Y_1)
        stone.putStone(position)
        assertThatThrownBy { stone.putStone(position) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("이미 놓여진 자리입니다.\n")
    }

    @Test
    fun `돌이 놓이지 않은 자리에 돌을 놓는다면 예외가 발생하지 않는다`() {
        val stone = BlackStone()
        val position = Position(X_A, Y_2)
        assertDoesNotThrow { stone.putStone(position) }
    }
}
