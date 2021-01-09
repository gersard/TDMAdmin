package cl.gerardomascayano.tdmadmin.core.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.format(format: String): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return formatter.format(this)
}