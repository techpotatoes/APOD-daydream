package com.lbbento.daydreamnasa.ui.presenter

import com.lbbento.daydreamnasa.ui.view.BaseServiceViewContract
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BaseServicePresenterTest {

    @Test
    fun onAttachedToWindowShouldSetViewAndCallOnSetContent() {
        val mockedView = mock(BaseServiceViewContract::class.java)
        val basePresenter = BaseServicePresenterTest()

        basePresenter.onAttachedToWindow(mockedView)

        verify(mockedView).setupScreen()
        assertEquals(basePresenter.mView, mockedView)
    }

    class BaseServicePresenterTest : BaseServicePresenter<BaseServiceViewContract>()
}