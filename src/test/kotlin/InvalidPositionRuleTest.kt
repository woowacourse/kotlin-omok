import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InvalidPositionRuleTest {
    @Test
    fun `돌을 둔 자리가 오목판의 범위를 넘어서면 실패한다`() {
        // given
        val playingBoard: PlayingBoard = PlayingBoard()
        val playerStone1: PlayerStone =
            PlayerStone(
                StoneColor.BLACK,
                Position(RowPosition(0), ColumnPosition(0)),
            )

        val actual = InvalidPositionRule().canPlace(playingBoard.board.value, playerStone1)
        val expected = PlaceResult.Failure.InvalidPosition

        assertThat(actual).isEqualTo(expected)
    }
}
