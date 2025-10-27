package com.cevichepicante.common

object FormatUtil {

    private val minSec = 60
    private val hourSec = minSec * 60

    /**
     *  @param second 초 단위 시간
     *  @return 소요 시간 형식의 문자열 반환 (예: sec == 4500 >> return 1시간 15분)
     */
    fun getDurationTimeString(second: Int): String {
        var hour = 0
        var min = 0
        var sec = 0

        when(second) {
            in Int.MIN_VALUE .. 0 -> {}
            in 1 until hourSec -> {
                val div = second.divRem(minSec)
                min = div.first
                sec = div.second
            }
            in hourSec .. Int.MAX_VALUE -> {
                val hourDiv = second.divRem(hourSec)
                hour = hourDiv.first
                val minDiv = hourDiv.second.divRem(minSec)
                min = minDiv.first
                sec = minDiv.second
            }
        }

        val timeString = StringBuilder()
        if(hour > 0) {
            timeString.let {
                it.append("${hour}시간")
                it.append(" ")
            }
        }

        if(min > 0) {
            timeString.let {
                it.append("${min}분")
                it.append(" ")
            }
        }

        if(sec > 0) {
            timeString.append("${sec}초")
        }

        return timeString.trim().toString()
    }

    fun Int.divRem(divisor: Int): Pair<Int, Int> {
        return Pair(
            first = (this / divisor),
            second = (this % divisor)
        )
    }
}