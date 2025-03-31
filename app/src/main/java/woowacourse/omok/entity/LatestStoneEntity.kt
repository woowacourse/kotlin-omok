package woowacourse.omok.entity

import omok.domain.place.Place

data class LatestStoneEntity(
    val id: Int,
    val nickname: String,
    val latestStone: Place,
)
