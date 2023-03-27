package domain.game

import domain.point.Point
import domain.rule.BlackRenjuRule
import domain.rule.OmokRule
import domain.rule.WhiteRenjuRule
import org.junit.jupiter.api.BeforeEach

class OmokTest {
    private lateinit var blackRule: OmokRule
    private lateinit var whiteRule: OmokRule

    private val blackPositions: MutableList<Point> by lazy {
        mutableListOf(
            Point(1, 1),
            Point(1, 2),
            Point(1, 3),
            Point(1, 4),
            Point(1, 5),
        )
    }
    private val whitePositions: MutableList<Point> by lazy {
        mutableListOf(
            Point(3, 3),
            Point(3, 4),
            Point(3, 10),
            Point(5, 5),
        )
    }

    @BeforeEach
    fun setUp() {
        blackRule = BlackRenjuRule()
        whiteRule = WhiteRenjuRule()
    }
}
