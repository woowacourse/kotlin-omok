package woowacourse.omok.model

import omok.library.OmokRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.model.state.CoordinateState
import woowacourse.omok.model.state.Turn
import woowacourse.omok.utils.OmokRuleMapper

class OmokRuleTest {
    private lateinit var boardLayout: MutableList<MutableList<CoordinateState>>
    private lateinit var omokRule: OmokRule

    @BeforeEach
    fun setUp() {
        boardLayout = newBoardLayout()
    }

    @Test
    fun `checkThreeThree를 확인한다 - 첫번째 경우`() {
        listOf(C12, E12, D13, D14).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.Black)
        }
        omokRule = OmokRuleMapper.map(Turn.Black, boardLayout, 15)
        assertThat(omokRule.isThreeThree(3, 11)).isEqualTo(true)
    }

    @Test
    fun `checkThreeThree를 확인한다 - 두번째 경우`() {
        listOf(M12, M10, N9, J9).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.Black)
        }
        omokRule = OmokRuleMapper.map(Turn.Black, boardLayout, 15)
        assertThat(omokRule.isThreeThree(11, 10)).isEqualTo(true)
    }

    @Test
    fun `checkThreeThree를 확인한다 - 세번째 경우`() {
        listOf(K3, K6, M4, N4).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.Black)
        }
        omokRule = OmokRuleMapper.map(Turn.Black, boardLayout, 15)
        assertThat(omokRule.isThreeThree(10, 3)).isEqualTo(true)
    }

    @Test
    fun `checkThreeThree를 확인한다 - 네번째 경우`() {
        listOf(B6, C5, E5, E6).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.Black)
        }
        omokRule = OmokRuleMapper.map(Turn.Black, boardLayout, 15)
        assertThat(omokRule.isThreeThree(4, 2)).isEqualTo(true)
    }

    @Test
    fun `countFourFour를 확인한다 - 첫번째 경우`() {
        listOf(C12, D12, G12, I12, J12).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.Black)
        }
        omokRule = OmokRuleMapper.map(Turn.Black, boardLayout, 15)
        assertThat(omokRule.isFourFour(5, 11)).isEqualTo(true)
    }

    @Test
    fun `countFourFour를 확인한다 - 두번째 경우`() {
        listOf(C10, C11, C12, E6, F5, G4).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.Black)
        }
        omokRule = OmokRuleMapper.map(Turn.Black, boardLayout, 15)
        assertThat(omokRule.isFourFour(2, 7)).isEqualTo(true)
    }

    @Test
    fun `countFourFour를 확인한다 - 세번째 경우`() {
        listOf(J6, J8, J9, J12).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.Black)
        }
        omokRule = OmokRuleMapper.map(Turn.Black, boardLayout, 15)
        assertThat(omokRule.isFourFour(9, 9)).isEqualTo(true)
    }

    @Test
    fun `checkMoreThanFive를 확인한다`() {
        listOf(A1, A2, A3, A4, A5).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.Black)
        }
        omokRule = OmokRuleMapper.map(Turn.Black, boardLayout, 15)
        assertThat(omokRule.isMoreThanFive(0, 5)).isEqualTo(true)
    }

    @Test
    fun `백돌이 오목인지 확인한다`() {
        listOf(A1, A2, A3, A4, A5).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.White)
        }
        omokRule = OmokRuleMapper.map(Turn.White, boardLayout, 15)

        assertThat(
            omokRule.isWin(0, 4),
        ).isEqualTo(true)
    }

    @Test
    fun `흑돌이 오목인지 확인한다`() {
        listOf(A1, A2, A3, A4, A5).map {
            boardLayout[it.x][it.y] = CoordinateState.Placed(turn = Turn.Black)
        }
        omokRule = OmokRuleMapper.map(Turn.Black, boardLayout, 15)
        assertThat(omokRule.isWin(0, 4)).isEqualTo(true)
    }
}
