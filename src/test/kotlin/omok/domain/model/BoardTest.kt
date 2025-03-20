package omok.domain.model

import io.kotest.matchers.shouldBe
import omok.diagonalDownWinStones
import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType
import omok.horizontalWinStones
import omok.verticalWinStones
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `바둑돌을 둔다`() {
        val position = Position(Column.from('A'), Row(1))
        board = board.placeStone(position, StoneType.BLACK)
        assertThat(board.stones[position]).isEqualTo(StoneType.BLACK)
    }

    @Test
    fun `바둑돌이 이미 존재하는 위치는 둘 수 없다`() {
        val position = Position(Column.from('A'), Row(1))
        board = board.placeStone(position, StoneType.BLACK)
        assertThatThrownBy {
            board.placeStone(
                position,
                StoneType.BLACK,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("바둑돌이 있는 곳에는 둘 수 없습니다.")
    }

    @Test
    fun `세로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        // Given
        val board = Board(horizontalWinStones)

        // Then
        board.checkWin() shouldBe true
    }

    @Test
    fun `대각선 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        // Given
        val board = Board(verticalWinStones)

        // Then
        board.checkWin() shouldBe true
    }

    @Test
    fun `가로 다섯 개의 연속된 돌이 있을 경우 승리한다`() {
        // Given
        val board = Board(diagonalDownWinStones)

        // Then
        board.checkWin() shouldBe true
    }

    @Test
    fun `바둑돌을 둘 위치가 빈 경우 바둑돌을 둘 수 있다`() {
        // Given
        val board = Board()
        val position = Position(Column.from('A'), Row(1))

        // Then
        board.canPlace(position) shouldBe true
    }

    @Test
    fun `마지막으로 둔 바둑돌을 반환한다`() {
        // Given
        val board = Board()
        val position = Position(Column.from('A'), Row(1))
        val stoneType = StoneType.BLACK

        // When
        val omokStone = OmokStone(position, stoneType)
        val newBoard = board.placeStone(omokStone.position, omokStone.stoneType)
        val lastStone = newBoard.lastStoneOrNull()

        // Then
        lastStone shouldBe omokStone
    }
}
