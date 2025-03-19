import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AlreadyExistRuleTest {
    @Test
    fun `돌이 이미 있는 위치에 돌을 두면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard()
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_A_ZERO)

        AlreadyExistRule().canPlace(playingBoard.board, playerStone1)

        playingBoard.placeStone(playerStone1)
        val actual = playingBoard.placeStone(playerStone1)
        val expected = PlaceResult.Failure.AlreadyExist

        assertThat(actual).isEqualTo(expected)
    }
}
