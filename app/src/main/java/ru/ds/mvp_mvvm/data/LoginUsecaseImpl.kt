package ru.ds.mvp_mvvm.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.ds.mvp_mvvm.domain.LoginApp
import ru.ds.mvp_mvvm.domain.LoginUseCase

class LoginUsecaseImpl(
    private val api: LoginApp,
) : LoginUseCase {
    override fun login(
        login: String,
        password: String
    ):Observable<Boolean> {
        return Observable
            .fromCallable { api.login(login, password) }
            .subscribeOn(Schedulers.io())
    }
}