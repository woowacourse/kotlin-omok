import woowacourse.omok.model.AddStoneStatus
import woowacourse.omok.model.Col
import woowacourse.omok.model.Position
import woowacourse.omok.model.Row
import woowacourse.omok.model.Stone
import woowacourse.omok.model.StoneColor
import model.judge.OmokJudge
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class OmokJudgeTest {
    @Test
    fun `같은 색 가로 돌이 5개 이상인지 판단할 수 있다`() {
        // given
        val stone = STONE_1A_BLACK
        // when
        val winningStones = listOf(STONE_1C_BLACK, STONE_1B_BLACK, STONE_1D_BLACK, STONE_1E_BLACK)
        val nothingStones = listOf(STONE_1C_BLACK, STONE_1B_WHITE, STONE_1D_BLACK, STONE_1E_BLACK)
        // result
        assertAll(
            { assertThat(OmokJudge.checkAddingStone(stone, winningStones)).isEqualTo(AddStoneStatus.IsWin) },
            { assertThat(OmokJudge.checkAddingStone(stone, nothingStones)).isEqualTo(AddStoneStatus.IsAble) },
        )
    }

    @Test
    fun `같은 색 세로 돌이 5개 이상인지 판단할 수 있다`() {
        // given
        val stone = STONE_3A_BLACK
        // when
        val winningStones = listOf(STONE_1A_BLACK, STONE_2A_BLACK, STONE_4A_BLACK, STONE_5A_BLACK)
        val nothingStones = listOf(STONE_1A_BLACK, STONE_2A_WHITE, STONE_4A_BLACK, STONE_5A_BLACK)
        // result
        assertAll(
            { assertThat(OmokJudge.checkAddingStone(stone, winningStones)).isEqualTo(AddStoneStatus.IsWin) },
            { assertThat(OmokJudge.checkAddingStone(stone, nothingStones)).isEqualTo(AddStoneStatus.IsAble) },
        )
    }

    @Test
    fun `증가하는 대각선 같은 색 돌이 5개 이상인지 판단할 수 있다`() {
        // given
        val stone = STONE_3C_BLACK
        // when
        val winningStones = listOf(STONE_1A_BLACK, STONE_2B_BLACK, STONE_4D_BLACK, STONE_5E_BLACK)
        val nothingStones = listOf(STONE_1A_BLACK, STONE_2B_WHITE, STONE_4D_BLACK, STONE_5E_BLACK)
        // result
        assertAll(
            { assertThat(OmokJudge.checkAddingStone(stone, winningStones)).isEqualTo(AddStoneStatus.IsWin) },
            { assertThat(OmokJudge.checkAddingStone(stone, nothingStones)).isEqualTo(AddStoneStatus.IsAble) },
        )
    }

    @Test
    fun `감소하는 대각선 같은 색 돌이 5개 이상인지 판단할 수 있다`() {
        // given
        val stone = STONE_3C_BLACK
        // when
        val winningStones = listOf(STONE_1E_BLACK, STONE_2D_BLACK, STONE_4B_BLACK, STONE_5A_BLACK)
        val nothingStones = listOf(STONE_1E_BLACK, STONE_2B_WHITE, STONE_4B_BLACK, STONE_5A_BLACK)
        // result
        assertAll(
            { assertThat(OmokJudge.checkAddingStone(stone, winningStones)).isEqualTo(AddStoneStatus.IsWin) },
            { assertThat(OmokJudge.checkAddingStone(stone, nothingStones)).isEqualTo(AddStoneStatus.IsAble) },
        )
    }

    @Test
    fun `흑돌 일 때 같은 색 돌이 6개 이상인지 (장목인지) 판단할 수 있다`() {
        // given
        val stone = STONE_3D_BLACK
        // when
        val overStones = listOf(STONE_1F_BLACK, STONE_2E_BLACK, STONE_4C_BLACK, STONE_5B_BLACK, STONE_6A_BLACK)
        val nothingStones = listOf(STONE_1F_BLACK, STONE_2E_WHITE, STONE_4C_BLACK, STONE_5B_BLACK, STONE_6A_BLACK)
        // result
        assertAll(
            { assertThat(OmokJudge.checkAddingStone(stone, overStones)).isEqualTo(AddStoneStatus.Failed.IsOverFive) },
            { assertThat(OmokJudge.checkAddingStone(stone, nothingStones)).isEqualTo(AddStoneStatus.IsAble) },
        )
    }

    @ParameterizedTest
    @CsvSource("12, 4", "3, 5", "4, 11", "11, 12")
    fun `흑돌일 때 삼삼을 판단할 수 있다`(
        newStoneRow: Int,
        newStoneCol: Int,
    ) {
        // given
        val stones =
            listOf(
                STONE_3C_BLACK,
                STONE_3D_BLACK,
                STONE_4E_BLACK,
                STONE_5C_BLACK,
                STONE_12C_BLACK,
                STONE_12E_BLACK,
                STONE_13D_BLACK,
                STONE_14D_BLACK,
                STONE_6B_BLACK,
                STONE_5E_BLACK,
                STONE_6E_BLACK,
                STONE_3K_BLACK,
                STONE_6K_BLACK,
                STONE_4M_BLACK,
                STONE_4N_BLACK,
                STONE_9N_BLACK,
                STONE_10M_BLACK,
                STONE_12M_BLACK,
                STONE_9J_BLACK,
                STONE_9I_WHITE,
            )
        // when
        val newStone = Stone(Position(Row.from(newStoneRow), Col.from(newStoneCol)), StoneColor.BLACK)
        assertThat(OmokJudge.checkAddingStone(newStone, stones)).isEqualTo(AddStoneStatus.Failed.IsThreeThree)
    }

    @ParameterizedTest
    @CsvSource("8, 3", "12, 6", "8, 9", "5, 8", "10,10")
    fun `흑돌일 때 사사를 판단할 수 있다`(
        newStoneRow: Int,
        newStoneCol: Int,
    ) {
        // given
        val stones =
            listOf(
                STONE_15C_BLACK,
                STONE_14C_BLACK,
                STONE_12C_BLACK,
                STONE_11C_BLACK,
                STONE_10C_BLACK,
                STONE_12D_BLACK,
                STONE_12G_BLACK,
                STONE_12I_BLACK,
                STONE_12J_BLACK,
                STONE_9J_BLACK,
                STONE_8J_BLACK,
                STONE_6J_BLACK,
                STONE_8K_BLACK,
                STONE_8H_BLACK,
                STONE_7H_BLACK,
                STONE_6H_BLACK,
                STONE_6E_BLACK,
                STONE_5E_BLACK,
                STONE_5F_BLACK,
                STONE_5G_BLACK,
                STONE_4G_BLACK,
                STONE_5D_WHITE,
                STONE_9H_WHITE,
            )
        // when
        val newStone = Stone(Position(Row.from(newStoneRow), Col.from(newStoneCol)), StoneColor.BLACK)
        // result
        assertThat(OmokJudge.checkAddingStone(newStone, stones)).isEqualTo(AddStoneStatus.Failed.IsFourFour)
    }
}
