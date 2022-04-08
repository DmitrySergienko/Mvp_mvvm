package ru.ds.mvp_mvvm.domain

interface AccessType {

    fun accessType(accessType:String):Boolean
    fun userRight(right: String):Boolean
}