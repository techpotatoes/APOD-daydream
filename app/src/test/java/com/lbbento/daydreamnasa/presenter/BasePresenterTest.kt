package com.lbbento.daydreamnasa.presenter

import com.lbbento.daydreamnasa.view.BaseViewContract
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class BasePresenterTest {

    @Test
    fun onAttachedToWindowShouldSetViewAndCallOnSetContent() {
        val mockedView = mock(BaseViewContract::class.java)
        val basePresenter = BasePresenterTest()

        basePresenter.onAttachedToWindow(mockedView)

        verify(mockedView).setScreenContent()
        assertEquals(basePresenter.mView, mockedView)
    }

    class BasePresenterTest : BasePresenter<BaseViewContract>()
}