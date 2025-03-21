package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Player2Test {
    @Test
    fun `플레이어가 수를 두면 플레이어의 색깔에 맞는 돌이 바둑판에 추가된다`() {
        val board = Board()
        val player = Player2(Color.BLACK)
        player.makeMove(board, Position(1, 1))

        val actual: Set<Stone2> = board.stones
        val expected: Set<Stone2> = setOf(Stone2(Position(1, 1), Color.BLACK))

        assertThat(actual).isEqualTo(expected)
    }
}
