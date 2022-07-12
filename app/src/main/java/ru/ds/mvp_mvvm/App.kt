package ru.ds.mvp_mvvm

import android.app.Application
import android.content.Context
import ru.ds.mvp_mvvm.data.LoginUsecaseImpl
import ru.ds.mvp_mvvm.data.api.MockLoginApiImpl
import ru.ds.mvp_mvvm.domain.LoginApp
import ru.ds.mvp_mvvm.domain.LoginUseCase

class App : Application() {
//by lazy (не создается сразу, толко при обращении)
    private val loginApp: LoginApp by lazy { MockLoginApiImpl() }
    val loginUseCase: LoginUseCase by lazy {
        LoginUsecaseImpl(app.loginApp) }

}
val Context.app: App
    get() {
        return applicationContext as App
    }