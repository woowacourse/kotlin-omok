package omok.domain.model

import io.kotest.matchers.shouldBe
import omok.horizontalWinStones
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.stone.OmokStone
import woowacourse.omok.domain.model.stone.StoneType

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `바둑돌을 둔다`() {
        // Given
        val position = Position.of(1, 1, board.size)
        val omokStone = OmokStone(position, StoneType.BLACK)

        // When
        board.placeStone(omokStone)

        // Then
        assertThat(board.stones.last()).isEqualTo(omokStone)
    }

    @Test
    fun `바둑돌이 이미 존재하는 위치는 둘 수 없다`() {
        // Given
        val position = Position.of(1, 1, board.size)
        val omokStone = OmokStone(position, StoneType.BLACK)
        board.placeStone(omokStone)

        // Then
        assertThatThrownBy { board.placeStone(omokStone) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("이미 바둑돌이 존재하는 곳에는 둘 수 없습니다.")
    }

    @Test
    fun `마지막에 추가된 돌을 반환한다`() {
        // Given
        val position = Position.of(1, 1, board.size)
        val omokStone = OmokStone(position, StoneType.BLACK)
        board.placeStone(omokStone)

        // Then
        board.getLastStone() shouldBe omokStone
    }

    @Test
    fun `오목판이 가득 찼음을 알 수 있다`() {
        // Given
        val miniBoard = Board(size = 2, _stones = horizontalWinStones)

        // Then
        miniBoard.isFull() shouldBe true
    }
}
