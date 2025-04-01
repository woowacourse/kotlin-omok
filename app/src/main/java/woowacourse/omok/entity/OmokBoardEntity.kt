package woowacourse.omok.entity

import omok.domain.place.Place

data class OmokBoardEntity(
    val id: Int,
    val nickname: String,
    val places: List<Place>,
)
