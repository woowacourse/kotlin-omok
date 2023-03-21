package controller

import domain.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ControllerTest {

    @Test
    fun `흑이 승리한다`() {
        // given
        val output: MutableList<String> = mutableListOf()

        val controller = Controller(
            inputView = TestInputView(
                listOf(
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
            outputView = TestOutputView { output.add(it) }
        )

        // when
        controller.run()

        // then
        assertThat(output).contains("흑 승")
    }

    @Test
    fun `해당 위치에 돌이 이미 존재한다`() {
        // given
        val output: MutableList<String> = mutableListOf()

        val controller = Controller(
            inputView = TestInputView(
                listOf(
                    Stone.create('A', 1),
                    Stone.create('B', 2),
                    Stone.create('A', 1),
                    Stone.create('A', 2),
                    Stone.create('D', 2),
                    Stone.create('A', 3),
                    Stone.create('E', 2),
                    Stone.create('A', 4),
                    Stone.create('N', 2),
                    Stone.create('A', 5),
                )
            ),
            outputView = TestOutputView { output.add(it) }
        )

        // when
        controller.run()

        // then
        assertThat(output).contains("해당 위치에 돌이 존재합니다.")
    }
}
