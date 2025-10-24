package com.cevichepicante.data.repository.impl

import android.content.Context
import android.util.Log
import com.cevichepicante.common.SharedPreferencesManager
import com.cevichepicante.data.R
import com.cevichepicante.data.model.Field
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.data.util.CsvHelper
import com.cevichepicante.database.dao.FoodDao
import com.cevichepicante.database.model.FoodEntity
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodRecipe
import com.cevichepicante.model.FoodSource
import com.cevichepicante.model.FoodType
import com.cevichepicante.model.RecipeMaterialData
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.regex.Pattern
import javax.inject.Inject

class FoodSourceRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dao: FoodDao
): FoodSourceRepository {

    @Inject lateinit var csvHelper: CsvHelper

    override suspend fun insertFoodSourceList(): Boolean {
        val isInsertedBefore = SharedPreferencesManager.isFoodDatabaseInserted(context)
        log("is food db inserted: $isInsertedBefore")
        if(isInsertedBefore) {
            return true
        }

        val rawIdList = listOf(R.raw.recipe_data_220701, R.raw.recipe_data_241226)
        val readList = mutableListOf<List<String>>()
        rawIdList.forEach { 
            readList.addAll(csvHelper.readAll(context, it))
        }

        /*
            Set data field index list.
         */
        val fieldList = readList.firstOrNull().orEmpty().let { list ->
            if(list.isNotEmpty()) {
                list.mapIndexed { pos, field ->
                    Field.entries.find { entry ->
                        entry.fieldName == field.removeInvisibleChars()
                    }
                }
            } else {
                log("empty field list")
                return false
            }
        }

        /*
            Set data of real content list.
         */
        val dataList = readList.let {
            if(it.size > 1) {
                it.subList(1, it.lastIndex)
            } else {
                log("empty data list")
                return false
            }
        }
        
        dao.insertFoodList(
            dataList.mapNotNull {
                val id = it.getOrNull(fieldList.indexOf(Field.SerialNo))
                val cookName = it.getOrNull(fieldList.indexOf(Field.CookingName))

                if(id.isNullOrEmpty() || cookName.isNullOrEmpty()) {
                    return@mapNotNull null
                }

                FoodEntity(
                    id = id,
                    recipeTitle = it.getOrNull(fieldList.indexOf(Field.RecipeTitle)),
                    cookingName = cookName,
                    registererId = it.getOrNull(fieldList.indexOf(Field.RegistererId)),
                    registererName = it.getOrNull(fieldList.indexOf(Field.RegistererName)),
                    viewCount = it.getOrNull(fieldList.indexOf(Field.ViewCount)),
                    recommendedCount = it.getOrNull(fieldList.indexOf(Field.RecommendedCount)),
                    scrappedCount = it.getOrNull(fieldList.indexOf(Field.ScrappedCount)),
                    cookingMethodCategory = it.getOrNull(fieldList.indexOf(Field.CookingMethodCategory)),
                    cookingOccasionCategory = it.getOrNull(fieldList.indexOf(Field.CookingOccasionCategory)),
                    cookingMaterialCategory = it.getOrNull(fieldList.indexOf(Field.CookingMaterialCategory)),
                    cookingKindCategory = it.getOrNull(fieldList.indexOf(Field.CookingKindCategory)),
                    cookingIntro = it.getOrNull(fieldList.indexOf(Field.CookingIntro)),
                    cookingMaterialContent = it.getOrNull(fieldList.indexOf(Field.CookingMaterialContent)),
                    cookingAmount = it.getOrNull(fieldList.indexOf(Field.CookingAmount)),
                    cookingLevel = it.getOrNull(fieldList.indexOf(Field.CookingLevel)),
                    cookingTime = it.getOrNull(fieldList.indexOf(Field.CookingTime)),
                    registeredTime = it.getOrNull(fieldList.indexOf(Field.RegisteredTime)),
                    imageUrl = it.getOrNull(fieldList.indexOf(Field.FoodImageUrl))
                )
            }.also {
                log("food list size: ${it.size}")
            }
        )
        SharedPreferencesManager.setFoodDatabaseInserted(context, true)
        return true
    }

    override suspend fun fetchFoodSourceList(): List<FoodSource> {
        return dao.getFoodList().map { 
            FoodSource(
                serialNo = it.id,
                recipeTitle = it.recipeTitle,
                cookingName = it.cookingName,
                registererId = it.registererId,
                registererName = it.registererName,
                viewCount = it.viewCount,
                recommendedCount = it.recommendedCount,
                scrappedCount = it.scrappedCount,
                cookingMethodCategory = it.cookingMethodCategory,
                cookingOccasionCategory = it.cookingOccasionCategory,
                cookingMaterialCategory = it.cookingMaterialCategory,
                cookingKindCategory = it.cookingKindCategory,
                cookingIntro = it.cookingIntro,
                cookingMaterialContent = it.cookingMaterialContent,
                cookingAmount = it.cookingAmount,
                cookingLevel = it.cookingLevel,
                cookingTime = it.cookingTime,
                registeredTime = it.registeredTime,
                imageUrl = it.imageUrl
            )
        }
    }

    override suspend fun fetchFoodList(): List<Food> {
        return dao.getFoodList().map {
            val foodType = FoodType(
                materialCategory = it.cookingMaterialCategory.orEmpty(),
                kindCategory = it.cookingKindCategory.orEmpty(),
                occasionCategory = it.cookingOccasionCategory.orEmpty()
            )
            Food(
                id = it.id,
                name = it.cookingName.orEmpty(),
                type = foodType,
                servingOccasion = it.cookingOccasionCategory.orEmpty()
            )
        }
    }

    override suspend fun fetchFoodListFiltered(type: FoodType): List<Food> {
        return dao.getFoodListFiltered(
            material = type.materialCategory,
            kind = type.kindCategory,
            occasion = type.occasionCategory
        ).map {
            val foodType = FoodType(
                materialCategory = it.cookingMaterialCategory.orEmpty(),
                kindCategory = it.cookingKindCategory.orEmpty(),
                occasionCategory = it.cookingOccasionCategory.orEmpty()
            )
            Food(
                id = it.id,
                name = it.cookingName.orEmpty(),
                type = foodType,
                servingOccasion = it.cookingOccasionCategory.orEmpty()
            )
        }
    }

    override suspend fun fetchCookingKindList(): List<String> {
        return dao.getCookingKindList()
    }

    override suspend fun fetchCookingMaterialList(): List<String> {
        return dao.getCookingMaterialList()
    }

    override suspend fun fetchCookingOccasionList(): List<String> {
        return dao.getCookingOccasionList()
    }

    override suspend fun fetchFoodDetail(foodId: String): FoodSource? {
        return dao.getFoodDetail(foodId).let {
            if(it != null) {
                FoodSource(
                    serialNo = it.id,
                    recipeTitle = it.recipeTitle,
                    cookingName = it.cookingName,
                    registererId = it.registererId,
                    registererName = it.registererName,
                    viewCount = it.viewCount,
                    recommendedCount = it.recommendedCount,
                    scrappedCount = it.scrappedCount,
                    cookingMethodCategory = it.cookingMethodCategory,
                    cookingOccasionCategory = it.cookingOccasionCategory,
                    cookingMaterialCategory = it.cookingMaterialCategory,
                    cookingKindCategory = it.cookingKindCategory,
                    cookingIntro = it.cookingIntro,
                    cookingMaterialContent = it.cookingMaterialContent,
                    cookingAmount = it.cookingAmount,
                    cookingLevel = it.cookingLevel,
                    cookingTime = it.cookingTime,
                    registeredTime = it.registeredTime,
                    imageUrl = it.imageUrl
                )
            } else {
                null
            }
        }
    }

    override suspend fun fetchFoodRecipe(foodId: String): FoodRecipe? {
        return dao.getFoodDetail(foodId).let {
            if(it != null) {
                FoodRecipe(
                    id = it.id,
                    name = it.cookingName.orEmpty(),
                    time = it.cookingTime.orEmpty(),
                    foodType = it.cookingKindCategory.orEmpty(),
                    level = it.cookingLevel.orEmpty(),
                    materialList = getMaterialList(it.cookingMaterialContent.orEmpty()).map { pair ->
                        RecipeMaterialData(
                            category = pair.first,
                            list = pair.second
                        )
                    }
                )
            } else {
                null
            }
        }
    }

    override fun getMaterialList(materialString: String): List<Pair<String, List<String>>> {
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

    private fun log(msg: String) {
        Log.d("JSY" ,"[${javaClass.simpleName}]: $msg")
    }

    private fun String.removeInvisibleChars(): String {
        return this.replace(Regex("[\\[{Z}\\p{C}]"), "")
    }
}