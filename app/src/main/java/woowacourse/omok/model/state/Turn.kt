package woowacourse.omok.model.state

sealed interface Turn {
    fun nextTurn(): Turn

    data object White : Turn {
        override fun nextTurn() = Black
    }

    data object Black : Turn {
        override fun nextTurn() = White
    }
}
