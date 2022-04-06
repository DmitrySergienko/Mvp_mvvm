package ru.ds.mvp_mvvm.domain

import ru.ds.mvp_mvvm.domain.entities.UserProfile

interface UserRepo {
    //Crate
    fun addUser(user: String)

    //Read
    fun getAllUsers():List<UserProfile>

    //Update
    fun changeUser(id: String, user:UserProfile)

    //Delete
    fun deleteUser(id: String)
    fun deleteAll()

}