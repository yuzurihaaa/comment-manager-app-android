package com.yuzuriha.jetpack.tryout.utilities

import java.util.*

fun String.containsIgnoreCase(keyword: String): Boolean =
    this.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))