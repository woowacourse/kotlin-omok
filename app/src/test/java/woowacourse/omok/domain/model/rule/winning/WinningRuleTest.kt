package woowacourse.omok.domain.model.rule.winning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.POSITION_FIVE_FIVE
import woowacourse.omok.POSITION_FIVE_ONE
import woowacourse.omok.POSITION_FOUR_FOUR
import woowacourse.omok.POSITION_FOUR_ONE
import woowacourse.omok.POSITION_ONE_FIVE
import woowacourse.omok.POSITION_ONE_FOUR
import woowacourse.omok.POSITION_ONE_ONE
import woowacourse.omok.POSITION_ONE_THREE
import woowacourse.omok.POSITION_ONE_TWO
import woowacourse.omok.POSITION_THREE_ONE
import woowacourse.omok.POSITION_THREE_THREE
import woowacourse.omok.POSITION_TWO_ONE
import woowacourse.omok.POSITION_TWO_TWO
import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.judge.WinningRule

class WinningRuleTest {
    private lateinit var omokGame: OmokGame

    @BeforeEach
    fun setup() {
        omokGame = OmokGame()
    }

    @Test
    fun `검정색의 돌이 가로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_FIVE)

        // when
        omokGame.placeStone(POSITION_ONE_ONE)
        omokGame.placeStone(POSITION_ONE_TWO)
        omokGame.placeStone(POSITION_ONE_THREE)
        omokGame.placeStone(POSITION_ONE_FOUR)
        omokGame.placeStone(POSITION_ONE_FIVE)

        val actual = WinningRule().perform(omokGame.game.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 세로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_FIVE_ONE)

        // when
        omokGame.placeStone(POSITION_ONE_ONE)
        omokGame.placeStone(POSITION_TWO_ONE)
        omokGame.placeStone(POSITION_THREE_ONE)
        omokGame.placeStone(POSITION_FOUR_ONE)
        omokGame.placeStone(POSITION_FIVE_ONE)

        val actual = WinningRule().perform(omokGame.game.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 대각선으로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_FIVE_FIVE)

        // when
        omokGame.placeStone(POSITION_ONE_ONE)
        omokGame.placeStone(POSITION_TWO_TWO)
        omokGame.placeStone(POSITION_THREE_THREE)
        omokGame.placeStone(POSITION_FOUR_FOUR)
        omokGame.placeStone(POSITION_FIVE_FIVE)

        val actual = WinningRule().perform(omokGame.game.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `나란히 있는 검은돌 4개 사이에 한 칸을 띄우고, 빈 공간에 돌을 두면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_THREE)

        // when
        omokGame.placeStone(POSITION_ONE_ONE)
        omokGame.placeStone(POSITION_ONE_TWO)

        omokGame.placeStone(POSITION_ONE_FOUR)
        omokGame.placeStone(POSITION_ONE_FIVE)

        val actual = WinningRule().perform(omokGame.game.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흰돌이 가로로 5개 있으면 흰색이 우승한다`() {
        // given
        omokGame.reverseTurn()
        val playerStone1: PlayerStone = PlayerStone(StoneColor.WHITE, POSITION_ONE_FIVE)

        // when
        omokGame.placeStone(POSITION_ONE_ONE)
        omokGame.placeStone(POSITION_ONE_TWO)
        omokGame.placeStone(POSITION_ONE_THREE)
        omokGame.placeStone(POSITION_ONE_FOUR)
        omokGame.placeStone(POSITION_ONE_FIVE)

        val actual = WinningRule().perform(omokGame.game.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.WHITE)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
