package model.domain

interface State {
    fun place(location: Location, board: Board): State
}
