package woowacourse.omok.model

import RuleAdaptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    private fun createBoard(
        board: Board,
        stones: List<Stone>,
    ) {
        stones.forEach { stone ->
            board.putStone(stone)
        }
    }

    @Test
    fun `오목판을 생성하면 기본적으로 15 x 15 사이즈를 갖는다`() {
        // given,when
        val stones = Stones()
        val rule = RuleAdaptor(stones)
        val board = Board(stones, rule)

        // then
        assertThat(board.width).isEqualTo(15)
        assertThat(board.height).isEqualTo(15)
    }

    @Test
    fun `오목판의 사이즈를 정해서 생성할 수 있다`() {
        // given,when
        val stones = Stones()
        val rule = RuleAdaptor(stones)
        val newBoard = Board(stones, rule, 30, 30)

        // then
        assertThat(newBoard.width).isEqualTo(30)
        assertThat(newBoard.height).isEqualTo(30)
    }

    @Test
    fun `오목판은 착수된 돌을 가지고 있다`() {
        // given
        val stones = Stones()
        val rule = RuleAdaptor(stones)
        val board = Board(stones, rule)
        val stone = Stone(Color.BLACK, COORDINATE_F8)

        // when
        board.putStone(stone)

        // then
        assertThat(board.stones.stones).contains(stone)
    }

    /*
     12 [ ] [ ] [ ] [ ] [ ]
     11 [ ] [ ] [ ] [ ] [ ]
     10 [ ] [ ] [●] [ ] [ ]
      9 [ ] [ ] [●] [ ] [ ]
      8 [●] [●] [X] [ ] [ ]
         D   E   F   G   H
     */
    @Test
    fun `흑 플레이어가 3-3을 만드는 경우, 착수할 수 없다`() {
        // given
        val stones = Stones()
        val rule = RuleAdaptor(stones)
        val board = Board(stones, rule)
        createBoard(board, samSamBlackStones)

        // when
        val stoneState = board.putStone(Stone(Color.BLACK, COORDINATE_F8))

        // then
        assertThat(stoneState).isEqualTo(StoneState.FailedPlaced("금수를 두었습니다."))
    }

    /*
     12 [ ] [ ] [ ] [ ] [ ]
     11 [ ] [ ] [ ] [●] [ ]
     10 [ ] [ ] [ ] [●] [ ]
      9 [ ] [ ] [ ] [●] [ ]
      8 [●] [●] [●] [X] [ ]
         C   D   E   F   G
    */
    @Test
    fun `흑 플레이어가 4-4을 만드는 경우, 착수할 수 없다`() {
        // given
        val stones = Stones()
        val rule = RuleAdaptor(stones)
        val board = Board(stones, rule)
        createBoard(board, fourFourBlackStones)

        // when
        val stoneState = board.putStone(Stone(Color.BLACK, COORDINATE_F8))

        assertThat(stoneState).isEqualTo(StoneState.FailedPlaced("금수를 두었습니다."))
    }

    /*
      10 [ ] [ ] [ ] [●] [ ]
       9 [ ] [ ] [ ] [●] [ ]
       8 [ ] [ ] [ ] [X] [ ]
       7 [ ] [ ] [ ] [●] [ ]
       6 [ ] [ ] [ ] [●] [ ]
       5 [ ] [ ] [ ] [●] [ ]
          C   D   E   F   G
    */
    @Test
    fun `흑 플레이어가 장목을 만드는 경우, 착수할 수 없다`() {
        // given
        val stones = Stones()
        val rule = RuleAdaptor(stones)
        val board = Board(stones, rule)
        createBoard(board, moreThanFiveBlackStones)

        // when
        val stoneState = board.putStone(Stone(Color.BLACK, COORDINATE_F8))

        assertThat(stoneState).isEqualTo(StoneState.FailedPlaced("금수를 두었습니다."))
    }

    /*
      11 [ ] [ ] [ ] [●] [ ]
      10 [ ] [ ] [ ] [●] [ ]
       9 [ ] [ ] [ ] [ ] [ ]
       8 [ ] [ ] [ ] [X] [ ]
       7 [ ] [ ] [ ] [●] [ ]
       6 [ ] [ ] [ ] [●] [ ]
       5 [ ] [ ] [ ] [ ] [ ]
       4 [ ] [ ] [ ] [●] [ ]
          C   D   E   F   G
     */
    @Test
    fun `흑 플레이어가 열린 4-4을 만드는 경우, 착수할 수 없다`() {
        // given
        val stones = Stones()
        val rule = RuleAdaptor(stones)
        val board = Board(stones, rule)
        createBoard(board, openFourFourBlackStones)

        // when
        val stoneState = board.putStone(Stone(Color.BLACK, COORDINATE_F8))

        // then
        assertThat(stoneState).isEqualTo(StoneState.FailedPlaced("금수를 두었습니다."))
    }

    @Test
    fun `백 플레이어는 렌주룰을 적용받지 않는다`() {
        // given
        val stones = Stones()
        val rule = RuleAdaptor(stones)
        val board = Board(stones, rule)
        createBoard(board, samSamWhiteStones)

        // when
        val stoneState = board.putStone(Stone(Color.WHITE, COORDINATE_F8))

        // then
        assertThat(stoneState).isEqualTo(StoneState.SuccessfulPlaced)
    }
}
