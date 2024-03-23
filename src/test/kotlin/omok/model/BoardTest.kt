package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

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

    /*
     12 [ ] [ ] [ ] [ ] [ ]
     11 [ ] [ ] [ ] [ ] [ ]
     10 [ ] [ ] [●] [ ] [ ]
      9 [ ] [ ] [●] [ ] [ ]
      8 [●] [●] [X] [ ] [ ]
         D   E   F   G   H
    * */
    @Test
    fun `흑 플레이어가 3-3을 만드는 경우, 착수할 수 없다`() {
        createBoard(samSamBlackStones)
        assertThrows<IllegalStateException> {
            board.putStone(blackStone)
        }
    }

    /*
     12 [ ] [ ] [ ] [ ] [ ]
     11 [ ] [ ] [ ] [●] [ ]
     10 [ ] [ ] [ ] [●] [ ]
      9 [ ] [ ] [ ] [●] [ ]
      8 [●] [●] [●] [X] [ ]
         C   D   E   F   G
    * */
    @Test
    fun `흑 플레이어가 4-4을 만드는 경우, 착수할 수 없다`() {
        createBoard(fourFourBlackStones)
        assertThrows<IllegalStateException> {
            board.putStone(blackStone)
        }
    }

    /*
      10 [ ] [ ] [ ] [●] [ ]
       9 [ ] [ ] [ ] [●] [ ]
       8 [ ] [ ] [ ] [X] [ ]
       7 [ ] [ ] [ ] [●] [ ]
       6 [ ] [ ] [ ] [●] [ ]
       5 [ ] [ ] [ ] [●] [ ]
          C   D   E   F   G
    * */
    @Test
    fun `흑 플레이어가 장목을 만드는 경우, 착수할 수 없다`() {
        createBoard(moreThanFiveBlackStones)
        assertThrows<IllegalStateException> {
            board.putStone(blackStone)
        }
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
    * */
    @Test
    fun `흑 플레이어가 열린 4-4을 만드는 경우, 착수할 수 없다`() {
        createBoard(openFourFourBlackStones)
        assertThrows<IllegalStateException> {
            board.putStone(blackStone)
        }
    }

    @Test
    fun `백 플레이어는 렌주룰을 적용받지 않는다`() {
        createBoard(samSamWhiteStones)
        assertDoesNotThrow {
            board.putStone(whiteStone)
        }
    }
}
