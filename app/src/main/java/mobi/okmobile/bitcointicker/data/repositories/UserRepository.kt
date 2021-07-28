package mobi.okmobile.bitcointicker.data.repositories

import mobi.okmobile.bitcointicker.data.firebase.FirebaseAuthUtil

class UserRepository(private val firebase: FirebaseAuthUtil) {

    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}