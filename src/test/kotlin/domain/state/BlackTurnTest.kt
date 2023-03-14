package domain.state

import domain.Stone
import domain.XCoordinate
import domain.YCoordinate
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class BlackTurnTest {

    @Test
    fun `오목이 만들어진 흑돌로 흑돌을 둘 수 있는 상태를 생성하면 에러가 발생한다`() {
        assertThatIllegalArgumentException().isThrownBy {
            BlackTurn(
                setOf(
                    Stone(XCoordinate.of('B'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('C'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('D'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('E'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('F'), YCoordinate.of(2)),
                ),
                setOf(
                    Stone(XCoordinate.of('B'), YCoordinate.of(12)),
                    Stone(XCoordinate.of('J'), YCoordinate.of(14)),
                    Stone(XCoordinate.of('H'), YCoordinate.of(3)),
                    Stone(XCoordinate.of('M'), YCoordinate.of(2)),
                ),
            )
        }.withMessage("게임이 진행되는 상태에서는 오목이 완성될 수 없습니다.")
    }

    @Test
    fun `오목이 만들어진 백돌로 흑돌을 둘 수 있는 상태를 생성하면 에러가 발생한다`() {
        assertThatIllegalArgumentException().isThrownBy {
            BlackTurn(
                setOf(
                    Stone(XCoordinate.of('B'), YCoordinate.of(12)),
                    Stone(XCoordinate.of('J'), YCoordinate.of(14)),
                    Stone(XCoordinate.of('H'), YCoordinate.of(3)),
                    Stone(XCoordinate.of('M'), YCoordinate.of(2)),
                ),
                setOf(
                    Stone(XCoordinate.of('B'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('C'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('D'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('E'), YCoordinate.of(2)),
                    Stone(XCoordinate.of('F'), YCoordinate.of(2)),
                ),
            )
        }.withMessage("게임이 진행되는 상태에서는 오목이 완성될 수 없습니다.")
    }

    @Test
    fun `둘 수 있는 곳에 흑돌을 뒀을 때 게임이 안끝나면 백돌을 둘 수 있는 상태를 반환한다`() {
        val state: State = BlackTurn(setOf(), setOf())
        val stone = Stone(XCoordinate.of('A'), YCoordinate.of(1))

        val actual = state.put(stone)

        assertThat(actual).isInstanceOf(WhiteTurn::class.java)
    }

    @Test
    fun `흑돌을 뒀을 때 가로로 5목 이상이 되면 흑 승리 상태를 반환한다`() {
        val state: State = BlackTurn(
            setOf(
                Stone(XCoordinate.of('B'), YCoordinate.of(2)),
                Stone(XCoordinate.of('C'), YCoordinate.of(2)),
                Stone(XCoordinate.of('D'), YCoordinate.of(2)),
                Stone(XCoordinate.of('E'), YCoordinate.of(2)),
            ),
            setOf(
                Stone(XCoordinate.of('B'), YCoordinate.of(12)),
                Stone(XCoordinate.of('J'), YCoordinate.of(14)),
                Stone(XCoordinate.of('H'), YCoordinate.of(3)),
                Stone(XCoordinate.of('M'), YCoordinate.of(2)),
            ),
        )

        val actual = state.put(Stone(XCoordinate.of('F'), YCoordinate.of(2)))

        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    @Test
    fun `흑돌을 뒀을 때 세로로 5목 이상이 되면 흑 승리 상태를 반환한다`() {
        val state: State = BlackTurn(
            setOf(
                Stone(XCoordinate.of('B'), YCoordinate.of(2)),
                Stone(XCoordinate.of('B'), YCoordinate.of(3)),
                Stone(XCoordinate.of('B'), YCoordinate.of(4)),
                Stone(XCoordinate.of('B'), YCoordinate.of(5)),
            ),
            setOf(
                Stone(XCoordinate.of('B'), YCoordinate.of(12)),
                Stone(XCoordinate.of('J'), YCoordinate.of(14)),
                Stone(XCoordinate.of('H'), YCoordinate.of(3)),
                Stone(XCoordinate.of('M'), YCoordinate.of(2)),
            ),
        )

        val actual = state.put(Stone(XCoordinate.of('B'), YCoordinate.of(6)))

        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    @Test
    fun `흑돌을 뒀을 때 왼쪽위에서 시작하는 대각선이 5목 이상이 되면 흑 승리 상태를 반환한다`() {
        val state: State = BlackTurn(
            setOf(
                Stone(XCoordinate.of('C'), YCoordinate.of(12)),
                Stone(XCoordinate.of('D'), YCoordinate.of(11)),
                Stone(XCoordinate.of('E'), YCoordinate.of(10)),
                Stone(XCoordinate.of('F'), YCoordinate.of(9)),
            ),
            setOf(
                Stone(XCoordinate.of('B'), YCoordinate.of(12)),
                Stone(XCoordinate.of('J'), YCoordinate.of(14)),
                Stone(XCoordinate.of('H'), YCoordinate.of(3)),
                Stone(XCoordinate.of('M'), YCoordinate.of(2)),
            ),
        )

        val actual = state.put(Stone(XCoordinate.of('G'), YCoordinate.of(8)))

        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }

    @Test
    fun `흑돌을 뒀을 때 왼쪽 아래에서 시작하는 대각선이 5목 이상이 되면 흑 승리 상태를 반환한다`() {
        val state: State = BlackTurn(
            setOf(
                Stone(XCoordinate.of('B'), YCoordinate.of(2)),
                Stone(XCoordinate.of('C'), YCoordinate.of(3)),
                Stone(XCoordinate.of('D'), YCoordinate.of(4)),
                Stone(XCoordinate.of('E'), YCoordinate.of(5)),
            ),
            setOf(
                Stone(XCoordinate.of('B'), YCoordinate.of(12)),
                Stone(XCoordinate.of('J'), YCoordinate.of(14)),
                Stone(XCoordinate.of('H'), YCoordinate.of(3)),
                Stone(XCoordinate.of('M'), YCoordinate.of(2)),
            ),
        )

        val actual = state.put(Stone(XCoordinate.of('F'), YCoordinate.of(6)))

        assertThat(actual).isInstanceOf(BlackWin::class.java)
    }
}
