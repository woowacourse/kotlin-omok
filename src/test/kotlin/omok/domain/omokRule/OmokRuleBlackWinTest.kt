package omok.domain.omokRule

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class OmokRuleBlackWinTest {
    @Test
    fun `검은 돌의 5개가 연속이면 승리다(수평)`() {
        val board = listOf(
            //     0  1  1  3  4  5  6  7  8  9 10  11 12 13 14
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  0
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  1
            listOf(0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  2
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  3
            listOf(0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0), //  4
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  5
            listOf(0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0), //  6
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  7
            listOf(0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0), //  8
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  9
            listOf(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0), // 10
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 11
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 12
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 13
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 14
        )

        assertAll(
            { assertThat(BlackWinRule.validate(board, Pair(5, 2))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(5, 4))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(5, 6))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(5, 8))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(5, 10))).isTrue },
        )
    }

    @Test
    fun `검은 돌의 5개가 연속이면 승리다(수직)`() {
        val board = listOf(
            //     0  1  1  3  4  5  6  7  8  9 10  11 12 13 14
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  0
            listOf(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  1
            listOf(0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  1
            listOf(0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  3
            listOf(0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0), //  4
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  5
            listOf(0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0), //  6
            listOf(0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0), //  7
            listOf(0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0), //  8
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0), //  9
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 10
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 11
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 12
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 13
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 14
        )

        assertAll(
            { assertThat(BlackWinRule.validate(board, Pair(1, 5))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(3, 5))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(5, 5))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(7, 5))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(9, 5))).isTrue },
        )
    }

    @Test
    fun `검은 돌의 5개가 연속이면 승리다(대각선)`() {
        val board = listOf(
            //     0  1  1  3  4  5  6  7  8  9 10  11 12 13 14
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  0
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  1
            listOf(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  1
            listOf(0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0), //  3
            listOf(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0), //  4
            listOf(0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0), //  5
            listOf(0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0), //  6
            listOf(0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0), //  7
            listOf(0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  8
            listOf(1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0), //  9
            listOf(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 10
            listOf(0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 11
            listOf(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 12
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 13
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 14
        )

        assertAll(
            { assertThat(BlackWinRule.validate(board, Pair(4, 1))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(4, 4))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(4, 7))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(4, 10))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(4, 13))).isTrue },
        )
    }

    @Test
    fun `검은 돌의 연속이 5개 이외면 승리가 아니다`() {
        val board = listOf(
            //     0  1  1  3  4  5  6  7  8  9 10  11 12 13 14
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  0
            listOf(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  1
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  1
            listOf(0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  3
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  4
            listOf(0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  5
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  6
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  7
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  8
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  9
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 10
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 11
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 12
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 13
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 14
        )

        assertAll(
            { assertThat(BlackWinRule.validate(board, Pair(3, 1))).isFalse },
            { assertThat(BlackWinRule.validate(board, Pair(4, 3))).isFalse },
            { assertThat(BlackWinRule.validate(board, Pair(5, 5))).isFalse },
            { assertThat(BlackWinRule.validate(board, Pair(6, 7))).isFalse },
        )
    }

    @Test
    fun `검은돌의 5개의 연속은 승리다`() {
        val board = listOf(
            //     0  1  1  3  4  5  6  7  8  9 10  11 12 13 14
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  0
            listOf(0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  1
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  1
            listOf(0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  3
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  4
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  5
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  6
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  7
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  8
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), //  9
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 10
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 11
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 12
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 13
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), // 14
        )

        assertAll(
            { assertThat(BlackWinRule.validate(board, Pair(5, 1))).isTrue },
            { assertThat(BlackWinRule.validate(board, Pair(6, 3))).isTrue },
        )
    }
}
