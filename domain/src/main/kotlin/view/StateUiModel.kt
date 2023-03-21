package view

import domain.State

fun selectState(state: State): String? {
    return when (state) {
        State.BLACK -> StateUiModel.BLACK.value
        State.WHITE -> StateUiModel.WHITE.value
        else -> null
    }
}

enum class StateUiModel(val value: String) {
    BLACK("흑"),
    WHITE("백")
}
