package ru.ds.mvp_mvvm.data.userrepo

import ru.ds.mvp_mvvm.domain.UserRepo
import ru.ds.mvp_mvvm.domain.entities.UserProfile

class CombineUserRepoImpl(
    private val localRepo: UserRepo,
    private val remoteRepo: UserRepo
):UserRepo {
    override fun addUser(user: String) {
        localRepo.addUser(user)
        remoteRepo.addUser(user)
    }

    override fun getAllUsers(): List<UserProfile> {
        TODO("Not yet implemented")
    }

    override fun changeUser(id: String, user: UserProfile) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}