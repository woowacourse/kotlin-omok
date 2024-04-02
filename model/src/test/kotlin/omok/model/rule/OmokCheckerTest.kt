package omok.model.rule

import omok.model.board.Board
import omok.model.position.Position
import omok.model.result.PutResult
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
        Board.resetBoard()
    }

    @MethodSource("오목 여부 판단 테스트 데이터 - 성공")
    @ParameterizedTest
    fun `오목 여부를 판단한다 - 오목일 때`(stonePositions: List<Position>) {
        // given
        val blackStone = BlackStone

        stonePositions.forEach {
            blackStone.putStone(it)
        }

        val lastPosition = Position(4, 4)
        // when
        val actual = OmokChecker.findOmok(lastPosition, StoneType.BLACK_STONE)
        val expected = PutResult.OMOK

        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @MethodSource("오목 여부 판단 테스트 데이터 - 실패")
    @ParameterizedTest
    fun `오목 여부를 판단한다 - 오목이 아닐 때`(stonePositions: List<Position>) {
        // given
        val blackStone = BlackStone
        val whiteStone = WhiteStone

        stonePositions.forEach {
            blackStone.putStone(it)
        }
        whiteStone.putStone(Position(12, 12))

        val lastPosition = Position(11, 11)
        // when
        val actual = OmokChecker.findOmok(lastPosition, StoneType.BLACK_STONE)
        val expected = PutResult.Running

        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun `오목 여부 판단 테스트 데이터 - 성공`() =
            listOf(
                Arguments.of(listOf(Position(1, 1), Position(2, 2), Position(3, 3), Position(4, 4), Position(5, 5))),
            )

        @JvmStatic
        fun `오목 여부 판단 테스트 데이터 - 실패`() =
            listOf(
                Arguments.of(listOf(Position(10, 10), Position(11, 11), Position(13, 13), Position(14, 14))),
            )
    }
}
