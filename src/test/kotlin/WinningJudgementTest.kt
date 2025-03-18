import domain.model.WinningJudgement
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WinningJudgementTest {
    @Test
    fun `세로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        val winningJudgement = WinningJudgement(verticalWinStones)
        assertThat(winningJudgement.checkWin()).isTrue()
    }

    @Test
    fun `대각선 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        val winningJudgement = WinningJudgement(diagonalDownWinStones)
        assertThat(winningJudgement.checkWin()).isTrue()
    }

    @Test
    fun `가로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        val winningJudgement = WinningJudgement(horizontalWinStones)
        assertThat(winningJudgement.checkWin()).isTrue()
    }
}
