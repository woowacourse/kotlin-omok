package omok.judgement

import omok.HorizontalAxis
import omok.Player
import omok.Position
import omok.Stone
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class LineRenjuJudgementTest {
    @Test
    fun `가로축에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        val player = Player(
            listOf(
                Stone(Position(HorizontalAxis.B, 3)),
                Stone(Position(HorizontalAxis.C, 3)),
                Stone(Position(HorizontalAxis.D, 3)),
                Stone(Position(HorizontalAxis.E, 3)),
                Stone(Position(HorizontalAxis.F, 3))
            )
        )
        val position = Position(HorizontalAxis.E, 3)
        val lineJudgement = LineJudgement(player, position)

        assertThat(lineJudgement.check()).isTrue
    }

    @Test
    fun `주 대각선 위에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        val player = Player(
            listOf(
                Stone(Position(HorizontalAxis.A, 6)),
                Stone(Position(HorizontalAxis.B, 7)),
                Stone(Position(HorizontalAxis.C, 8)),
                Stone(Position(HorizontalAxis.D, 9)),
                Stone(Position(HorizontalAxis.E, 10))
            )
        )
        val position = Position(HorizontalAxis.B, 7)
        val lineJudgement = LineJudgement(player, position)

        assertThat(lineJudgement.check()).isTrue
    }

    @Test
    fun `주 대각선 아래에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        // given
        val player = Player(
            listOf(
                Stone(Position(HorizontalAxis.I, 2)),
                Stone(Position(HorizontalAxis.J, 3)),
                Stone(Position(HorizontalAxis.K, 4)),
                Stone(Position(HorizontalAxis.L, 5)),
                Stone(Position(HorizontalAxis.M, 6))
            )
        )
        val position = Position(HorizontalAxis.L, 5)
        val lineJudgement = LineJudgement(player, position)

        assertThat(lineJudgement.check()).isTrue
    }

    @Test
    fun `부 대각선 위에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        // given
        val player = Player(
            listOf(
                Stone(Position(HorizontalAxis.M, 8)),
                Stone(Position(HorizontalAxis.L, 9)),
                Stone(Position(HorizontalAxis.K, 10)),
                Stone(Position(HorizontalAxis.J, 11)),
                Stone(Position(HorizontalAxis.I, 12))
            )
        )
        val position = Position(HorizontalAxis.K, 10)
        val lineJudgement = LineJudgement(player, position)

        assertThat(lineJudgement.check()).isTrue
    }

    @Test
    fun `부 대각선 아래에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        // given
        val player = Player(
            listOf(
                Stone(Position(HorizontalAxis.H, 3)),
                Stone(Position(HorizontalAxis.G, 4)),
                Stone(Position(HorizontalAxis.F, 5)),
                Stone(Position(HorizontalAxis.E, 6)),
                Stone(Position(HorizontalAxis.D, 7))
            )
        )
        val position = Position(HorizontalAxis.G, 4)
        val lineJudgement = LineJudgement(player, position)

        assertThat(lineJudgement.check()).isTrue
    }
}
