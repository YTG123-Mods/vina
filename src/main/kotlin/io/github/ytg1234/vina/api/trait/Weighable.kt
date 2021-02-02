package io.github.ytg1234.vina.api.trait

import org.jetbrains.annotations.Range

interface Weighable {
    val weight: @Range(from = 0, to = 15) Int
}
