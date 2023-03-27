package woowacourse.omok.data.db.table

import domain.domain.CoordinateState
import woowacourse.omok.data.db.Column
import woowacourse.omok.data.db.SQLiteTable

object GameTableSimplify : SQLiteTable {
    // 흑돌 백돌 변환용 상수
    const val BLACK_NUMBER = 1
    const val WHITE_NUMBER = 2

    const val COORDINATE_STATE_CONVERSION_ERROR = "DB 사용중 올바르지 않은 CoordinateState 변환이 일어났습니다."

    const val ID = "id"
    const val TURN = "turn"
    const val BOARD = "board"

    override val name: String = "gamesimplify"
    override val scheme: List<Column> = listOf(
        Column(ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
        Column(TURN, "int"),
        Column(BOARD, "TEXT"),
    )

    fun numberToCoordinateState(number: Int): CoordinateState {
        if (number == BLACK_NUMBER) return CoordinateState.BLACK
        if (number == WHITE_NUMBER) return CoordinateState.WHITE
        throw IllegalStateException(COORDINATE_STATE_CONVERSION_ERROR)
    }

    fun coordinateStateToNumber(coordinateState: CoordinateState): Int {
        if (coordinateState == CoordinateState.BLACK) return BLACK_NUMBER
        if (coordinateState == CoordinateState.WHITE) return WHITE_NUMBER
        throw IllegalStateException(COORDINATE_STATE_CONVERSION_ERROR)
    }
}
