
import omok.domain.omokRule.FourFourRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class OmokRuleFourTest {
    @Test
    fun `열린 4이 두개 이상이면 둘 수 없다`() {
        val board = listOf(
            //     0  1  2  3  4  5  6  7  8  9 10  11 12 13 14
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  0
            listOf(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0), //  1
            listOf(0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0), //  2
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  3
            listOf(0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  4
            listOf(0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0), //  5
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0), //  6
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0), //  7
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0), //  8
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0), //  9
            listOf(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 10
            listOf(0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0), // 11
            listOf(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0), // 12
            listOf(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 13
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 14
        )
        assertAll(
            { assertThat(FourFourRule.validate(board, Pair(3, 3))).isTrue },
            { assertThat(FourFourRule.validate(board, Pair(3, 11))).isTrue },
            { assertThat(FourFourRule.validate(board, Pair(9, 4))).isTrue },
            { assertThat(FourFourRule.validate(board, Pair(10, 11))).isTrue },
        )
    }
}
