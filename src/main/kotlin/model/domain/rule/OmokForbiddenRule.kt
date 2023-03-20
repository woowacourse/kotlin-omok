package model.domain.rule

import model.domain.tools.Location

interface OmokForbiddenRule {
    fun isForbidden(location: Location): Boolean
}
