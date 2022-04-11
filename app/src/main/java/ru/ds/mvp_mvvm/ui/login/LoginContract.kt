package ru.ds.mvp_mvvm.ui.login

import androidx.annotation.MainThread

/**
 * MVP  - Model View Presenter
 * 1) Восстановление состояния
 * 2) Большая* связность
 * 3) Многословность - проверки на нулл, постоянные вызовы вью
 *
 * (M) <- (P) <-> (V)
 *
 * MVVM - Model View ViewModel
 * (M) <- (VM) <- (V)
 */

//    Интерфейс вью больше не нужен в MVVM
//    Её роль достаётся подпискам во ViewModel

interface LoginContract {
/*
    interface View {
        @MainThread
        fun setSuccess()
        @MainThread
        fun setError(error: String)
        @MainThread
        fun showProgress()
        @MainThread
        fun hideProgress()
        @MainThread
        fun showSymbols()

    }*/


    interface ViewModel {

        var shouldShowProgress: Boolean
        var isSuccess: Boolean
        var errorText: String?

        @MainThread
        fun onLogin(login: String, password: String)
        @MainThread
        fun onCredentialsChanged()
        @MainThread
        private fun checkCredentials(login: String, password: String): Boolean {
            return login == password
        }

    }
}