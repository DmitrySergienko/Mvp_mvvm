package ru.ds.mvp_mvvm.data.api

import ru.ds.mvp_mvvm.domain.LoginApp

class MockLoginApiImpl: LoginApp {
    override fun login(login: String, password: String): Boolean {
        return login == password
    }

    override fun register(login: String, password: String, email: String): Boolean {
        return login.isNotEmpty()
    }

    override fun logout(): Boolean {
        return  true
    }

    override fun forgotPassword(login: String): Boolean {
        return false
    }
}