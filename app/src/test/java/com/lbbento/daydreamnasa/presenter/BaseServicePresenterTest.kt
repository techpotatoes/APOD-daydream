package com.lbbento.daydreamnasa.presenter

import com.lbbento.daydreamnasa.view.BaseServiceViewContract
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class BaseServicePresenterTest {

    @Test
    fun onAttachedToWindowShouldSetViewAndCallOnSetContent() {
        val mockedView = mock(BaseServiceViewContract::class.java)
        val basePresenter = BaseServicePresenterTest()

        basePresenter.onAttachedToWindow(mockedView)

        verify(mockedView).setScreenContent()
        assertEquals(basePresenter.mView, mockedView)
    }

    class BaseServicePresenterTest : BaseServicePresenter<BaseServiceViewContract>()
}