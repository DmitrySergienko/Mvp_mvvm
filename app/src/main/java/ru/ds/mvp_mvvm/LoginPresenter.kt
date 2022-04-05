package ru.ds.mvp_mvvm

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import java.lang.Thread.sleep


class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())


    @MainThread
    override fun onAttach(view: LoginContract.View) {
        this.view = view

    }

    @MainThread
    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(1000)
            uiHandler.post {
                view?.hideProgress()
                if (checkCredentials(login, password)) {
                    view?.setSuccess()

                } else {
                    view?.setError("Wrong Password")

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