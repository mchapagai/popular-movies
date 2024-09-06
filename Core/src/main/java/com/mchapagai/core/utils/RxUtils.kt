package com.mchapagai.core.utils

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Transform an existing [io.reactivex.Observable] to apply appropriate [Schedulers]
 */

object RxUtils {

    /**
     * Applies a function to upstream [io.reactivex.Observable] and returns an [io.reactivex.ObservableSource]
     * with optionally different element type.
     *
     * @param upstream the upstream Observable instance
     * @return the transformed [io.reactivex.ObservableSource] instance
     */
    fun <T> applyObservableSchedulers(): ObservableTransformer<T, T> =
        ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    /**
     * Applies a function to upstream [io.reactivex.Flowable] and returns an [io.reactivex.FlowableSource]
     */
    fun <T> applyFlowableSchedulers(): FlowableTransformer<T, T> =
        FlowableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    /**
     * Applies a function to upstream [io.reactivex.Single] and returns an [io.reactivex.SingleSource]
     */
    fun <T> applySingleSchedulers(): SingleTransformer<T, T> =
        SingleTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
}