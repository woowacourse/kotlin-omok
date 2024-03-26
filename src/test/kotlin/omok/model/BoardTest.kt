package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BoardTest {
    private lateinit var board: Board
    private lateinit var blackStone: Stone
    private lateinit var whiteStone: Stone

    private fun createBoard(stones: List<Stone>) {
        stones.forEach { stone ->
            board.putStone(stone)
        }
    }

    @BeforeEach
    fun setUp() {
        board = Board(Stones())
        blackStone = Stone(black, COORDINATE_F8)
        whiteStone = Stone(white, COORDINATE_F8)
    }

    @Test
    fun `오목판은 착수된 돌을 가지고 있다`() {
        board.putStone(blackStone)
        assertThat(board.stones.stones).contains(blackStone)
    }

    @ParameterizedTest
    @CsvSource("1,0", "16,5", "3,-9")
    fun `착수하려는 위치가 바둑판 바깥이라면 착수할 수 없다`(
        row: Int,
        col: Int,
    ) {
        val outsideStone = Stone(black, Coordinate(Row(row), Column(col)))
        assertThat(board.putStone(outsideStone)).isEqualTo(StoneState.OUTSIDE_THE_BOARD)
    }

    @Test
    fun `착수하려는 위치에 이미 다른 돌이 있다면 착수할 수 없다`() {
        board.putStone(blackStone)
        assertThat(board.putStone(whiteStone)).isEqualTo(StoneState.OCCUPIED)
    }

    @Test
    fun `흑 플레이어가 3-3을 만드는 경우는 금수이다`() {
        createBoard(samSamBlackStones)
        assertThat(board.putStone(blackStone)).isEqualTo(StoneState.FORBIDDEN)
    }

    @Test
    fun `흑 플레이어가 4-4을 만드는 경우는 금수이다`() {
        createBoard(fourFourBlackStones)
        assertThat(board.putStone(blackStone)).isEqualTo(StoneState.FORBIDDEN)
    }

    @Test
    fun `흑 플레이어가 장목을 만드는 경우는 금수이다`() {
        createBoard(moreThanFiveBlackStones)
        assertThat(board.putStone(blackStone)).isEqualTo(StoneState.FORBIDDEN)
    }

    @Test
    fun `흑 플레이어가 열린 4-4을 만드는 경우는 금수이다`() {
        createBoard(openFourFourBlackStones)
        assertThat(board.putStone(blackStone)).isEqualTo(StoneState.FORBIDDEN)
    }

    @Test
    fun `백 플레이어는 렌주룰을 적용받지 않는다`() {
        createBoard(samSamWhiteStones)
        assertThat(board.putStone(whiteStone)).isEqualTo(StoneState.PLACED)
    }
}
