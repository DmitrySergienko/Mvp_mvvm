package ru.ds.mvp_mvvm.utils

import android.os.Handler


//typealias это подставновка в коде для удобства (встречает Subscriber<E> подставляет Unit
//private typealias Subscriber<E> = Pair<Handler, (E?) -> Unit>

public class Subscriber<E>(
    private val handler: Handler,
    private val callback: (E?) -> Unit
){
    fun invoke(value: E?) {
        handler.post {
            callback.invoke(value)
        }
    }
}

class Publisher<E> {

    // так как подписчивов может быть несколько делаем список подписчиков
    //используем Set чтобы нельзя было добавть один подписчик два раза
    private val subscribers: MutableSet<Subscriber<E?>> = mutableSetOf()
    public var value: E? = null
        private set //value можно установить только из Publisher
    private var hasFirstValue = false

    //осуществляем подписку на изменеия
    fun subscribe(handler: Handler, callback: (E?)->Unit) {
        val subscriber = Subscriber(handler, callback)
        subscribers.add(subscriber)

        //если value не null, то заполняем его (обновляем дату)
        //если кто то подписался он будет получать последнее значение
        if (hasFirstValue) {
             subscriber.invoke(value)

        }
    }

    /*//для обнуления подписки (чтобы не тратить память и не создавать каждый раз новый экран(активити или фрагмент)
    fun unsubscribe(subscriber: Subscriber<E>) {
        subscribers.remove(subscriber)
    }*/
    //для обнуления подписки (чтобы не тратить память и не создавать каждый раз новый экран(активити или фрагмент)
    //полностью освобождаем список
    fun unsubscribeAll() {
        subscribers.clear()
    }

    //метод для отправки значения
    //когда дата обновлена сообщаем подпискику (subscriber)
    //что значение извенилось
    fun post(value: E) {
        hasFirstValue = true
        this.value = value // запоминаем значение
        subscribers.forEach { it.invoke(value) }

    }

}

/*interface Subscriber<E> {
    fun post(value: E?)

}*/
//лямбда

/*fun foo(lambda: (Int) -> Double) {
    lambda(22) //можно так вызывать
    lambda.invoke(22) //можно так вызвать
}*/