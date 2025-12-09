package com.cevichepicante.data.repository

import java.util.regex.Pattern

abstract class BaseFoodSourceRepository: FoodSourceRepository {

    fun getMaterialList(materialString: String): List<Pair<String, List<String>>> {
        if(materialString.isEmpty()) {
            return listOf()
        }

        val result = mutableListOf<Pair<String, List<String>>>()
        val categoryPattern = Pattern.compile("\\[(.*?)\\]")
        val categoryMatcher = categoryPattern.matcher(materialString)
        val categories = mutableListOf<Pair<Int, String>>()
        while (categoryMatcher.find()) {
            categories.add(
                Pair(
                    categoryMatcher.start(),
                    categoryMatcher.group(1).orEmpty().trim()
                )
            )
        }

        categories.forEachIndexed { pos, pair ->
            val start = pair.first.plus("[${pair.second}]".length)
            val end = categories.getOrNull(pos.inc())?.first?: materialString.length
            val contentString = materialString.substring(start, end).trim()
            val contentList = mutableListOf<String>()
            contentString.split("|").forEach {
                val material = it.trim()
                if(material.isNotEmpty()) {
                    contentList.add(material)
                }
            }
            result.add(Pair(pair.second, contentList))
        }

        return result
    }
}