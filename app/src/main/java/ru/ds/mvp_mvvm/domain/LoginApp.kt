package ru.ds.mvp_mvvm.domain

interface LoginApp {

    fun login(login: String, password:String): Boolean
    fun register(login:String, password: String, email:String): Boolean
    fun logout(): Boolean
    fun forgotPassword(login: String): Boolean
}