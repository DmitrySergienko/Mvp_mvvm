package ru.ds.mvp_mvvm.data

import android.os.Handler
import ru.ds.mvp_mvvm.domain.LoginApp
import ru.ds.mvp_mvvm.domain.LoginUseCase

class LoginUsecaseImpl(
    private val api: LoginApp,
    private val uiHandler: Handler
) : LoginUseCase {

    override fun login(login: String, password: String, callback: (Boolean) -> Unit) {
        Thread {
            val result = api.login(login, password)
            uiHandler.post { callback(result) }

        }.start()
    }
}