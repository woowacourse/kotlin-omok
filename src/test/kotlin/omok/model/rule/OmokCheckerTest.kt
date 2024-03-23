package omok.model.rule

import omok.model.board.Board
import omok.model.position.Col
import omok.model.position.Position
import omok.model.position.Row
import omok.model.stone.BlackStone
import omok.model.stone.Stone
import omok.model.stone.WhiteStone
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class OmokCheckerTest {
    @BeforeEach
    fun setUp() {
        repeat(Board.BOARD_SIZE) { row ->
            repeat(Board.BOARD_SIZE) { col ->
                Board.board[row][col] = Stone.NONE
            }
        }
    }

    @MethodSource("오목 여부 판단 테스트 데이터 - 성공")
    @ParameterizedTest
    fun `오목 여부를 판단한다 - 오목일 때`(stonePositions: List<Position>) {
        // given
        val blackStone = BlackStone()

        stonePositions.forEach {
            blackStone.putStone(it)
        }

        val lastPosition = Position(Row(4), Col.from(4))
        // when
        val actual = OmokChecker.findOmok(lastPosition, Stone.BLACK_STONE)
        val expected = true

        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @MethodSource("오목 여부 판단 테스트 데이터 - 실패")
    @ParameterizedTest
    fun `오목 여부를 판단한다 - 오목이 아닐 때`(stonePositions: List<Position>) {
        // given
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()

        stonePositions.forEach {
            blackStone.putStone(it)
        }
        whiteStone.putStone(Position(Row(12), Col.from(12)))

        val lastPosition = Position(Row(11), Col.from(11))
        // when
        val actual = OmokChecker.findOmok(lastPosition, Stone.BLACK_STONE)
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
                        Position(Row(1), Col.from(1)),
                        Position(Row(2), Col.from(2)),
                        Position(Row(3), Col.from(3)),
                        Position(Row(4), Col.from(4)),
                        Position(Row(5), Col.from(5)),
                    ),
                ),
            )

        @JvmStatic
        fun `오목 여부 판단 테스트 데이터 - 실패`() =
            listOf(
                Arguments.of(
                    listOf(
                        Position(Row(10), Col.from(10)),
                        Position(Row(11), Col.from(11)),
                        Position(Row(13), Col.from(13)),
                        Position(Row(14), Col.from(14)),
                    ),
                ),
            )
    }
}
