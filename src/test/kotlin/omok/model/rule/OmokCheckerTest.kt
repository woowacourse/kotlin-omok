package omok.model.rule

import X_A
import X_B
import X_C
import X_D
import X_E
import X_K
import X_L
import X_M
import X_N
import X_O
import Y_1
import Y_10
import Y_11
import Y_12
import Y_13
import Y_14
import Y_2
import Y_3
import Y_4
import Y_5
import omok.model.board.Board
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.StoneType
import omok.model.stone.WhiteStone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class OmokCheckerTest {
    @BeforeEach
    fun setUp() {
        Board.reset()
    }

    @MethodSource("오목 여부 판단 테스트 데이터 - 성공")
    @ParameterizedTest
    fun `오목이라면 true를 반환한다`(stonePositions: List<Position>) {
        // given
        val blackStone = BlackStone()

        stonePositions.forEach {
            blackStone.putStone(it)
        }

        val lastPosition = Position(X_D, Y_4)
        // when
        val actual = OmokChecker.findOmok(lastPosition, StoneType.BLACK_STONE)
        val expected = true

        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @MethodSource("오목 여부 판단 테스트 데이터 - 실패")
    @ParameterizedTest
    fun `오목이 아니라면 false를 반환한다`(stonePositions: List<Position>) {
        // given
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()

        stonePositions.forEach {
            blackStone.putStone(it)
        }
        whiteStone.putStone(Position(X_M, Y_12))

        val lastPosition = Position(X_L, Y_11)
        // when
        val actual = OmokChecker.findOmok(lastPosition, StoneType.BLACK_STONE)
        val expected = false

        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun `오목 여부 판단 테스트 데이터 - 성공`() =
            listOf(
                Arguments.of(
                    listOf(
                        Position(X_A, Y_1),
                        Position(X_B, Y_2),
                        Position(X_C, Y_3),
                        Position(X_D, Y_4),
                        Position(X_E, Y_5),
                    ),
                ),
            )

        @JvmStatic
        fun `오목 여부 판단 테스트 데이터 - 실패`() =
            listOf(
                Arguments.of(
                    listOf(
                        Position(X_K, Y_10),
                        Position(X_L, Y_11),
                        Position(X_N, Y_13),
                        Position(X_O, Y_14),
                    ),
                ),
            )
    }
}
