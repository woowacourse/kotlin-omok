package controller

import domain.Stone
import view.InputView

class TestInputView(
    private val stone: List<Stone>
) : InputView {
    private var index = 0
    override fun readPosition(): Stone = stone[index++]
}
