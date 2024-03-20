package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Player(val color: Color) {
    private var _isWin = false
    val isWin: Boolean
        get() = _isWin

    fun playTurn(board: Board, coordinate: Coordinate) {
        val stone = Stone(color, coordinate)
        putStoneOnBoard(board, stone)
        _isWin = checkOmok(board.stones, stone)
    }

    private fun putStoneOnBoard(board: Board, stone: Stone) {
        board.putStone(stone)
    }

    private fun checkOmok(stones: Stones, stone: Stone): Boolean {
        return stones.findOmok(stone)
    }
}

class PlayerTest {
    private lateinit var board: Board
    private lateinit var stones: Stones
    private lateinit var player: Player

    @BeforeEach
    fun setUp() {
        stones = Stones()
        board = Board(stones)
        stones.putStone(Stone(Color.BLACK, Coordinate(Row.from("6"), Column.from("F"))))
        stones.putStone(Stone(Color.BLACK, Coordinate(Row.from("7"), Column.from("G"))))
        stones.putStone(Stone(Color.BLACK, Coordinate(Row.from("8"), Column.from("H"))))
        stones.putStone(Stone(Color.BLACK, Coordinate(Row.from("9"), Column.from("I"))))

    }

    @Test
    fun `플레이어는 본인의 색상을 가지고 있다`() {
        val color = Color.BLACK

        player = Player(color)

        assertThat(player.color).isEqualTo(color)
    }

    @Test
    fun `플레이어 차례가 되면 돌을 보드에 착수 한다`() {
        val coordinate = Coordinate(Row.from("2"), Column.from("J"))
        player = Player(Color.BLACK)

        player.playTurn(board, coordinate)

        assertThat(stones.stones.size).isEqualTo(5)
    }

    @Test
    fun `플레이어의 차례에 오목이 만들어지면 승리한다`() {
        val coordinate = Coordinate(Row.from("10"), Column.from("J"))
        player = Player(Color.BLACK)

        player.playTurn(board, coordinate)

        assertThat(player.isWin).isTrue()
    }

    @Test
    fun `플레이어의 차례에 장목이 만들어지면 승리한다`() {
        val coordinateFirst = Coordinate(Row.from("11"), Column.from("K"))
        val coordinateSecond = Coordinate(Row.from("10"), Column.from("J"))
        player = Player(Color.BLACK)

        player.playTurn(board, coordinateFirst)
        player.playTurn(board, coordinateSecond)

        assertThat(player.isWin).isTrue()
    }
}