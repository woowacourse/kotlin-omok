package omok.domain.rule.winning

import omok.POSITION_FIVE_FIVE
import omok.POSITION_FIVE_ONE
import omok.POSITION_FOUR_FOUR
import omok.POSITION_FOUR_ONE
import omok.POSITION_ONE_FIVE
import omok.POSITION_ONE_FOUR
import omok.POSITION_ONE_ONE
import omok.POSITION_ONE_THREE
import omok.POSITION_ONE_TWO
import omok.POSITION_THREE_ONE
import omok.POSITION_THREE_THREE
import omok.POSITION_TWO_ONE
import omok.POSITION_TWO_TWO
import omok.domain.omokboard.PlayingBoard
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import omok.domain.rule.place.AlreadyExistStoneRule
import omok.domain.rule.place.ExternalRule
import omok.domain.rule.place.InvalidPositionRule
import omok.domain.rule.place.PlaceRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_ONE)
        val playerStone2: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_TWO)
        val playerStone3: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_THREE)
        val playerStone4: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_FOUR)
        val playerStone5: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_FIVE)

        // when
        playingBoard.placeStone(placeRules, playerStone1)
        playingBoard.placeStone(placeRules, playerStone2)
        playingBoard.placeStone(placeRules, playerStone3)
        playingBoard.placeStone(placeRules, playerStone4)
        playingBoard.placeStone(placeRules, playerStone5)

        val actual = WinningRule().perform(playingBoard.board, playerStone5)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 세로로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_ONE)
        val playerStone2: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_TWO_ONE)
        val playerStone3: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_THREE_ONE)
        val playerStone4: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_FOUR_ONE)
        val playerStone5: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_FIVE_ONE)

        // when
        playingBoard.placeStone(placeRules, playerStone1)
        playingBoard.placeStone(placeRules, playerStone2)
        playingBoard.placeStone(placeRules, playerStone3)
        playingBoard.placeStone(placeRules, playerStone4)
        playingBoard.placeStone(placeRules, playerStone5)

        val actual = WinningRule().perform(playingBoard.board, playerStone5)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `검은돌이 대각선으로 5개 있으면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_ONE)
        val playerStone2: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_TWO_TWO)
        val playerStone3: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_THREE_THREE)
        val playerStone4: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_FOUR_FOUR)
        val playerStone5: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_FIVE_FIVE)

        // when
        playingBoard.placeStone(placeRules, playerStone1)
        playingBoard.placeStone(placeRules, playerStone2)
        playingBoard.placeStone(placeRules, playerStone3)
        playingBoard.placeStone(placeRules, playerStone4)
        playingBoard.placeStone(placeRules, playerStone5)

        val actual = WinningRule().perform(playingBoard.board, playerStone5)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `나란히 있는 검은돌 4개 사이에 한 칸을 띄우고, 빈 공간에 돌을 두면 검정색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_ONE)
        val playerStone2: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_TWO)
        val playerStone3: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_THREE)
        val playerStone4: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_FOUR)
        val playerStone5: PlayerStone = PlayerStone(StoneColor.BLACK, POSITION_ONE_FIVE)

        // when
        playingBoard.placeStone(placeRules, playerStone1)
        playingBoard.placeStone(placeRules, playerStone2)

        playingBoard.placeStone(placeRules, playerStone4)
        playingBoard.placeStone(placeRules, playerStone5)

        val actual = WinningRule().perform(playingBoard.board, playerStone3)
        val expected = JudgeResult.Finished.Win(StoneColor.BLACK)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `흰돌이 가로로 5개 있으면 흰색이 우승한다`() {
        // given
        val playerStone1: PlayerStone = PlayerStone(StoneColor.WHITE, POSITION_ONE_ONE)
        val playerStone2: PlayerStone = PlayerStone(StoneColor.WHITE, POSITION_ONE_TWO)
        val playerStone3: PlayerStone = PlayerStone(StoneColor.WHITE, POSITION_ONE_THREE)
        val playerStone4: PlayerStone = PlayerStone(StoneColor.WHITE, POSITION_ONE_FOUR)
        val playerStone5: PlayerStone = PlayerStone(StoneColor.WHITE, POSITION_ONE_FIVE)

        // when
        playingBoard.placeStone(placeRules, playerStone1)
        playingBoard.placeStone(placeRules, playerStone2)
        playingBoard.placeStone(placeRules, playerStone3)
        playingBoard.placeStone(placeRules, playerStone4)
        playingBoard.placeStone(placeRules, playerStone5)

        val actual = WinningRule().perform(playingBoard.board, playerStone5)
        val expected = JudgeResult.Finished.Win(StoneColor.WHITE)

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
