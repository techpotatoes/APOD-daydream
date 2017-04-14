package com.lbbento.daydreamnasa.presenter

import com.lbbento.daydreamnasa.view.BaseViewContract
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class BasePresenterTest {

    @Test
    fun onAttachedToWindowShouldSetViewAndCallOnSetContent() {
        val mockedView = mock<BaseViewContract> {}
        val basePresenter = BasePresenterTest()

        basePresenter.onAttachedToWindow(mockedView)

        verify(mockedView).setScreenContent()
        assertEquals(basePresenter.mView, eq(mockedView))
    }

    class BasePresenterTest : BasePresenter<BaseViewContract>()
}