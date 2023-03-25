package model.domain.rule.omokForbiddenRule

import model.domain.tools.Location

interface OmokForbiddenRule {
    fun isForbidden(location: Location): Boolean
}
