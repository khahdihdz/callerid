package com.khahdihdz.callerid

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class PhoneNormalizationTest {
    @Test fun stripsSeparators() {
        val normalized = "+84 912-345-678".replace(Regex("[\\s().-]"), "")
        assertEquals("+84912345678", normalized)
    }

    @Test fun rejectsLetters() {
        assertFalse("090abc".matches(Regex("^\\+?[0-9]+$")))
    }
}
