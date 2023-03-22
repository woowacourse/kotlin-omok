package omok.domain.omokRule.adapter

import omok.domain.OmokBoard
import omok.domain.OmokPoint

interface RenjuRule {
    fun isForbidden(omokBoard: OmokBoard, omokPoint: OmokPoint): Boolean
}
