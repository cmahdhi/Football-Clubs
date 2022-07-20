package com.chiheb.footballclubs.core.bases

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BasePresenter<T : BaseView> : CoroutineScope {

    private var job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    var view: T? = null

    fun attachView(view: T) {
        this.view = view
        this.job = Job()
    }

    fun detachView() {
        this.view = null
        job.cancel()
    }

    fun notifyView(action: () -> Unit) {
        launch {
            withContext(Dispatchers.Main) {
                action()
            }
        }
    }
}