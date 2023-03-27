package domain.player

import domain.point.Point
import domain.point.Points
import domain.rule.OmokRule
import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.StoneColor

class WhitePlayer(state: PlayerState = PlayingState(), rule: OmokRule) : Player(state, rule) {
    override fun putStone(stone: Point, otherStones: Points): Player = WhitePlayer(state.add(stone, otherStones, rule), rule)

    override fun getStoneColor(): StoneColor = StoneColor.WHITE
}
