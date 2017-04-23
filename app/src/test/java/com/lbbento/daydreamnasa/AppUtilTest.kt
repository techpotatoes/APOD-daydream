package com.lbbento.daydreamnasa

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.security.InvalidParameterException

@RunWith(MockitoJUnitRunner::class)
class AppUtilTest {

    @Test
    fun shouldExtractYoutubeIdGivenYoutubeUrl() {
        assertEquals(AppUtil.extractYoutubeId("https://www.youtube.com/watch?v=12345"), "12345")
    }

    @Test(expected = Exception::class)
    fun shouldThrowExceptionWhenNotYoutubeUrl() {
        AppUtil.extractYoutubeId("https://www.something.com/")
    }

    @Test(expected = Exception::class)
    fun shouldThrowExceptionWhenUrlIsNotValid() {
        AppUtil.extractYoutubeId("423423425//dfd")
    }

    @Test(expected = InvalidParameterException::class)
    fun shouldThrowInvalidParameterExceptionWhenIdIsBlank() {
        AppUtil.extractYoutubeId("https://www.youtube.com/watch?invalidParam=12345")
    }
}