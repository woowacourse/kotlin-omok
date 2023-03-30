package domain.rule

import domain.Team

object RenjuRule : Rule(
    mapOf(
        Team.BLACK to listOf(Not33Constraint, Not44Constraint, NotLongMokConstraint)
    )
)
