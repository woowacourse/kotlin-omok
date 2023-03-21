package view

import domain.State

fun State.toUiModel(): StateUiModel? {
    return when (this) {
        State.BLACK -> StateUiModel.BLACK
        State.WHITE -> StateUiModel.WHITE
        else -> null
    }
}

enum class StateUiModel(val value: String) {
    BLACK("흑"),
    WHITE("백")
}
