package omok.model

class WhiteStonePlayer : Player() {
    override fun checkContinuity(stone: Stone): Boolean {
        val directions =
            arrayOf(
                Point(0, 1),
                Point(1, 0),
                Point(1, 1),
                Point(-1, 1),
            )

        directions.forEach { direction ->
            var count = 1
            // 정방향 탐색
            count += countStones(stone.point, direction)
            // 역방향 탐색
            count += countStones(stone.point, Point(-direction.row, -direction.col))

            if (count >= 5) return true // 연속된 돌이 5개 이상이면 true 반환
        }
        return false // 연속된 돌이 5개 미만이면 false 반환
    }
}
