package ru.ds.mvp_mvvm

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import java.lang.Thread.sleep


class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var isSuccess: Boolean = false
    private var errorText: String = ""

    @MainThread
    override fun onAttach(view: LoginContract.View) {
        this.view = view
        if (isSuccess) {
            view.setSuccess()
        } else {
            view.setError(errorText)
        }
    }

    @MainThread
    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(3000)
            uiHandler.post {
                view?.hideProgress()
                if (checkCredentials(login, password)) {
                    view?.setSuccess()
                    isSuccess = true
                    errorText = ""
                } else {
                    view?.setError("Wrong Password")
                    isSuccess = false
                    errorText ="Wrong Password"
                }
            }

        }.start()
    }

    @MainThread
    override fun onCredentialsChanged() {
        TODO("Not yet implemented")
    }

    @MainThread
    private fun checkCredentials(login: String, password: String): Boolean {
        return login == password
    }
}