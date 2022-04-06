package ru.ds.mvp_mvvm.domain

import androidx.annotation.WorkerThread
// @WorkerThread работает в фоне
interface LoginApp {
    @WorkerThread
    fun login(login: String, password: String): Boolean

    @WorkerThread
    fun register(login: String, password: String, email: String): Boolean

    @WorkerThread
    fun logout(): Boolean

    @WorkerThread
    fun forgotPassword(login: String): Boolean
}