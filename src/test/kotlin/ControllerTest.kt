import domain.OmokBoard
import domain.State
import domain.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import view.InputViewInterface
import view.OutputViewInterface

class ControllerTest {
    @Test
    fun `흑돌이 승리한다`() {
        // given
        val output: MutableList<String> = mutableListOf()

        val controller = Controller(
            inputView = fakeInputView(
                mutableListOf(
                    Stone.create('A', 1),
                    Stone.create('B', 2),
                    Stone.create('A', 2),
                    Stone.create('D', 2),
                    Stone.create('A', 3),
                    Stone.create('E', 2),
                    Stone.create('A', 4),
                    Stone.create('N', 2),
                    Stone.create('A', 5),
                )
            ),
            outputView = fakeOutputView { output.add(it) }
        )

        // when
        controller.run()

        // then
        assertThat(output).contains("흑돌 승")
    }

    @Test
    fun `백돌이 승리한다`() {
        // given
        val output: MutableList<String> = mutableListOf()

        val controller = Controller(
            inputView = fakeInputView(
                mutableListOf(
                    Stone.create('A', 1),
                    Stone.create('B', 1),
                    Stone.create('C', 2),
                    Stone.create('B', 2),
                    Stone.create('A', 3),
                    Stone.create('B', 3),
                    Stone.create('A', 4),
                    Stone.create('B', 4),
                    Stone.create('A', 5),
                    Stone.create('B', 5),
                )
            ),
            outputView = fakeOutputView { output.add(it) }
        )

        // when
        controller.run()

        // then
        assertThat(output).contains("백돌 승")
    }

    class fakeInputView(val stones: MutableList<Stone>) : InputViewInterface {
        override fun readPosition(): Stone {
            return stones.removeFirst()
        }
    }

    class fakeOutputView(val onPrintResult: (String) -> Unit) : OutputViewInterface {
        override fun printStart() {}

        override fun printDuplicate() {
            onPrintResult("중복입니다")
        }

        override fun printForbidden() {
            onPrintResult("금수입니다")
        }

        override fun printOmokState(omokBoard: OmokBoard, nextState: State, stone: Stone) {}

        override fun printWinner(state: State) {
            if (state == State.BLACK) onPrintResult("흑돌 승")
            if (state == State.WHITE) onPrintResult("백돌 승")
        }
    }
}
