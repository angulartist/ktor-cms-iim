package belabes.mohamed.cms.services

import belabes.mohamed.cms.Model
import belabes.mohamed.cms.model.User

class AuthService(private val model: Model) {
    fun getUserByUsername(username: String): User? = model.getUserByUsername(username)
}