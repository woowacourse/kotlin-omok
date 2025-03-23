package omok.model

import omok.model.game.Game
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class GameTest {
    private val game = Game()

    @Test
    fun `처음으로 착수하는 돌의 색을 확인할 수 있다`() {
        assertThat(game.getTurn()).isEqualTo(StoneColor.BLACK)
    }

    @Test
    fun `다음 턴으로 착수하는 돌의 색을 확인할 수 있다`() {
        val position = Position(Row(1), Col(2))
        game.place(position)
        assertThat(game.getTurn()).isEqualTo(StoneColor.WHITE)
    }

    @Test
    fun `돌을 원하는 위치에 착수할 수 있다`() {
        val position = Position(Row(5), Col(5))

        assertDoesNotThrow { game.place(position) }
    }

    @Test
    fun `마지막으로 착수한 돌을 확인할 수 있다`() {
        val position = Position(Row(5), Col(5))
        val stoneColor = game.getTurn()
        game.place(position)

        val lastStone = game.getLastStone()

        assertThat(lastStone?.position).isEqualTo(position)
        assertThat(lastStone?.stoneColor).isEqualTo(stoneColor)
    }

    @Test
    fun `착수한 후에 오목인지 아닌지 확인할 수 있다`() {
        val position1 = Position(Row(1), Col(1)) // Black
        val position2 = Position(Row(2), Col(2)) // White
        val position3 = Position(Row(1), Col(2)) // Black
        val position4 = Position(Row(9), Col(3)) // White
        val position5 = Position(Row(1), Col(3)) // Black
        val position6 = Position(Row(4), Col(2)) // White
        val position7 = Position(Row(1), Col(4)) // Black
        val position8 = Position(Row(7), Col(2)) // White
        val position9 = Position(Row(1), Col(5)) // Black

        val positions =
            listOf(
                position1,
                position2,
                position3,
                position4,
                position5,
                position6,
                position7,
                position8,
                position9,
            ) // Black
        for (i in positions) {
            game.place(i)
        }
        assertThat(game.isOmok()).isTrue()
    }
}
