package omok.model

import omok.model.game.Game
import omok.model.stone.Point
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameTest {
    @Test
    fun `플레이어가 수를 두면 마지막 돌이 바뀐다`() {
        val game = Game()
        game.play(Stone(Point(8, 8), StoneColor.WHITE))

        val actual: Stone = game.lastStone

        val expected = Stone(Point(8, 8), StoneColor.WHITE)

        assertThat(actual).isEqualTo(expected)
    }
}
