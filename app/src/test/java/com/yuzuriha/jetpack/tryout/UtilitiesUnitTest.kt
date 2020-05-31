package com.yuzuriha.jetpack.tryout

import com.yuzuriha.jetpack.tryout.utilities.containsIgnoreCase
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilitiesUnitTest {
    @Test
    fun stringIgnoreCase() {
        assertEquals("This Contains some rAndom cases".containsIgnoreCase("random"), true)
    }
}