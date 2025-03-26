package woowacourse.omok.domain.rule.winning

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
import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.rule.judge.DrawRule
import woowacourse.omok.domain.rule.judge.JudgeResult
import woowacourse.omok.domain.rule.judge.JudgeRule
import woowacourse.omok.domain.rule.judge.WinningRule
import woowacourse.omok.domain.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.rule.place.ExternalRule
import woowacourse.omok.domain.rule.place.InvalidPositionRule
import woowacourse.omok.domain.rule.place.PlaceRule

class WinningRuleTest {
    private lateinit var playingBoard: PlayingBoard
    private lateinit var placeRules: List<PlaceRule>
    private lateinit var judgeRules: List<JudgeRule>

    @BeforeEach
    fun setup() {
        playingBoard = PlayingBoard()
        placeRules = listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule())
        judgeRules = listOf(WinningRule(), DrawRule())
    }

    @Test
    fun `검정색의 돌이 가로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_FIVE)

        // when
        playingBoard.placeStone(placeRules, POSITION_ONE_ONE)
        playingBoard.placeStone(placeRules, POSITION_ONE_TWO)
        playingBoard.placeStone(placeRules, POSITION_ONE_THREE)
        playingBoard.placeStone(placeRules, POSITION_ONE_FOUR)
        playingBoard.placeStone(placeRules, POSITION_ONE_FIVE)

        val actual = WinningRule().perform(playingBoard.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 세로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_FIVE_ONE)

        // when
        playingBoard.placeStone(placeRules, POSITION_ONE_ONE)
        playingBoard.placeStone(placeRules, POSITION_TWO_ONE)
        playingBoard.placeStone(placeRules, POSITION_THREE_ONE)
        playingBoard.placeStone(placeRules, POSITION_FOUR_ONE)
        playingBoard.placeStone(placeRules, POSITION_FIVE_ONE)

        val actual = WinningRule().perform(playingBoard.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 대각선으로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_FIVE_FIVE)

        // when
        playingBoard.placeStone(placeRules, POSITION_ONE_ONE)
        playingBoard.placeStone(placeRules, POSITION_TWO_TWO)
        playingBoard.placeStone(placeRules, POSITION_THREE_THREE)
        playingBoard.placeStone(placeRules, POSITION_FOUR_FOUR)
        playingBoard.placeStone(placeRules, POSITION_FIVE_FIVE)

        val actual = WinningRule().perform(playingBoard.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `나란히 있는 검은돌 4개 사이에 한 칸을 띄우고, 빈 공간에 돌을 두면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_THREE)

        // when
        playingBoard.placeStone(placeRules, POSITION_ONE_ONE)
        playingBoard.placeStone(placeRules, POSITION_ONE_TWO)

        playingBoard.placeStone(placeRules, POSITION_ONE_FOUR)
        playingBoard.placeStone(placeRules, POSITION_ONE_FIVE)

        val actual = WinningRule().perform(playingBoard.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흰돌이 가로로 5개 있으면 흰색이 우승한다`() {
        // given
        playingBoard.reverseTurn()
        val playerStone1: PlayerStone = PlayerStone(StoneColor.WHITE, POSITION_ONE_FIVE)

        // when
        playingBoard.placeStone(placeRules, POSITION_ONE_ONE)
        playingBoard.placeStone(placeRules, POSITION_ONE_TWO)
        playingBoard.placeStone(placeRules, POSITION_ONE_THREE)
        playingBoard.placeStone(placeRules, POSITION_ONE_FOUR)
        playingBoard.placeStone(placeRules, POSITION_ONE_FIVE)

        val actual = WinningRule().perform(playingBoard.board, playerStone1)
        val expected = JudgeResult.Finished.Win(StoneColor.WHITE)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
