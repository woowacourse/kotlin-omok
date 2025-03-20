package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rule.wrapper.point.Point

class GameTest {
    @Test
    fun `플레이어가 수를 두면 마지막 돌이 바뀐다`() {
        val game = Game(BlackPlayer(), WhitePlayer())
        game.play(Point(8, 8))

        val actual: Stone = game.lastStone

        val expected = Stone(Point(8, 8), StoneColor.BLACK)

        assertThat(actual).isEqualTo(expected)
    }
}
