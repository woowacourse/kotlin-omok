package model.domain.state

import model.domain.Board
import model.domain.Location

class BlackTurn(private val blackBoard: Board) : State {
    override fun place(location: Location, board: Board): State {
        // board 를 보고 놓을 수 있는지 확인
        // 가능하면 board 에 추가

        return BlackTurn(blackBoard)
    }
}
