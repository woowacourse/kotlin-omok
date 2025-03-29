package woowacourse.omok.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.Full

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board(RenjuRuleAdapter())
    }

    @Test
    fun `오목판은 15개의 행을 가진다`() {
        assertThat(board.grid.size).isEqualTo(15)
    }

    @Test
    fun `오목판은 15개의 열을 가진이다`() {
        assertThat(board.grid[0].size).isEqualTo(15)
    }

    @Test
    fun `오목판은 225칸이다`() {
        assertThat(board.grid.flatten().size).isEqualTo(225)
    }

    @Test
    fun `오목판은 오목돌을 원하는 위치에 놓는다`() {
        board.put(Position(8, 9), StoneType.BLACK)
        val expected = StoneType.BLACK
        assertThat(board.grid[8][9]).isEqualTo(expected)
    }

    @Test
    fun `오목판이 다 채워지면 ture를 반환한다`() {
        Full.forEach { board.put(it.position, it.color) }
        assertThat(board.isFull()).isTrue()
    }

//    @Test
//    fun `오목판은 흑돌 33 금수를 막는다1`() {
//        ThreeThree.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(D12, StoneType.BLACK) }
//    }
//
//    @Test
//    fun `오목판은 흑돌 33 금수를 막는다2`() {
//        ThreeThree.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(E3, StoneType.BLACK) }
//    }
//
//    @Test
//    fun `오목판은 흑돌 33 금수를 막는다3`() {
//        ThreeThree.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(L11, StoneType.BLACK) }
//    }
//
//    @Test
//    fun `오목판은 흑돌 33 금수를 막는다4`() {
//        ThreeThree.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(K4, StoneType.BLACK) }
//    }

//    @Test
//    fun `오목판은 흑돌 44 금수를 막는다1`() {
//        FourFour.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(C13, StoneType.BLACK) }
//    }
//
//    @Test
//    fun `오목판은 흑돌 44 금수를 막는다2`() {
//        FourFour.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(C8, StoneType.BLACK) }
//    }
//
//    @Test
//    fun `오목판은 흑돌 44 금수를 막는다3`() {
//        FourFour.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(F12, StoneType.BLACK) }
//    }
//
//    @Test
//    fun `오목판은 흑돌 44 금수를 막는다4`() {
//        FourFour.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(J10, StoneType.BLACK) }
//    }
//
//    @Test
//    fun `오목판은 흑돌 44 금수를 막는다5`() {
//        FourFour.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(I8, StoneType.BLACK) }
//    }
//
//    @Test
//    fun `오목판은 흑돌 44 금수를 막는다6`() {
//        FourFour.forEach { board.put(it.position, it.color) }
//        assertThrows<IllegalArgumentException> { board.put(H5, StoneType.BLACK) }
//    }
}
