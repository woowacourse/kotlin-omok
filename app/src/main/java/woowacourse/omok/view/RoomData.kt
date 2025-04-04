package woowacourse.omok.view

import woowacourse.omok.model.Stone

data class RoomData(
    val Id : Int,
    val nickname: String,
    var stoneCount : Int,
    var stones : List<Stone>
)
