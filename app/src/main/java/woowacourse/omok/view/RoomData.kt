package woowacourse.omok.view

import model.Stone

data class RoomData(
    val Id : Int,
    val nickname: String,
    var stoneCount : Int,
    var stones : List<Stone>
)