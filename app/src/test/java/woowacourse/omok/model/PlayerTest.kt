package woowacourse.omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PlayerTest {
    private lateinit var board: Board
    private lateinit var stones: Stones
    private lateinit var player: Player

    @BeforeEach
    fun setUp() {
        stones = Stones()
        board = Board(stones)
        stones.putStone(Stone(Color.BLACK, COORDINATE_F5))
        stones.putStone(Stone(Color.BLACK, COORDINATE_F6))
        stones.putStone(Stone(Color.BLACK, COORDINATE_F7))
        stones.putStone(Stone(Color.BLACK, COORDINATE_F9))
    }

    @Test
    fun `플레이어는 본인의 색상을 가지고 있다`() {
        val color = Color.BLACK

        player = Player(color)

        assertThat(player.color).isEqualTo(color)
    }

    @Test
    fun `플레이어는 자기 색깔의 돌을 반환한다`() {
        fun inputCoordinate(): () -> Coordinate = { COORDINATE_A1 }

        player = Player(Color.BLACK)
        val stone = player.getStone(inputCoordinate())

        assertThat(player.color).isEqualTo(stone.color)
    }

    @Test
    fun `플레이어의 차례에 오목이 만들어지면 승리한다`() {
        fun inputCoordinate(): () -> Coordinate = { COORDINATE_F8 }

        player = Player(Color.BLACK)
        val stone = player.getStone(inputCoordinate())
        board.putStone(stone)
        player.checkOmok(stones, stone)

        assertThat(player.isWin).isTrue()
    }

    @Test
    fun `플레이어의 차례에 장목이 만들어지면 승리하지 못한다`() {
        player = Player(Color.BLACK)

        board.putStone(Stone(black, COORDINATE_F8))
        player.checkOmok(stones, Stone(black, COORDINATE_F10))

        assertThat(player.isWin).isFalse()
    }
}
