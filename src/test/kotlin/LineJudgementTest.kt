import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class LineJudgementTest {
    @Test
    fun `가로축에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        // given
        val player = Player()
        val position = Position(HorizontalAxis.E, 3)
        val lineJudgement = LineJudgement(player, position)

        // when
        player.put(WhiteStone(Position(HorizontalAxis.B, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.C, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.D, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.E, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.F, 3)))

        // then
        assertThat(lineJudgement.checkHorizontal()).isTrue
    }
}
