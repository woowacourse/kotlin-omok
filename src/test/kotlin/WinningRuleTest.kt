import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WinningRuleTest {
    @Test
    fun `같은 색의 돌이 가로로 5개 있으면 Finish를 반환한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard()
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_ONE)
        val playerStone2: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_TWO)
        val playerStone3: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_THREE)
        val playerStone4: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_FOUR)
        val playerStone5: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_FIVE)

        playingBoard.placeStone(playerStone1)
        playingBoard.placeStone(playerStone2)
        playingBoard.placeStone(playerStone3)
        playingBoard.placeStone(playerStone4)
        playingBoard.placeStone(playerStone5)

        val actual = WinningRule().canPlace(playingBoard.board, playerStone5)
        val expected = PlaceResult.Success.Finish(GameResult.WIN_BLACK)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `같은 색의 돌이 세로로 5개 있으면 Finish한다`() {
    }

    @Test
    fun `같은 색의 돌이 대각선으로 5개 있으면 Finish를 반환한다`() {
    }

    @Test
    fun `나란히 있는 같은 색의 돌 4개 사이에 한 칸을 띄우고, 빈 공간에 돌을 두면 Finish를 반환한다`() {
    }
}
