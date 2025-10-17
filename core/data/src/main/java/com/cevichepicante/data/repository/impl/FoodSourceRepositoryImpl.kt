package com.cevichepicante.data.repository.impl

import android.content.Context
import android.util.Log
import com.cevichepicante.data.R
import com.cevichepicante.data.model.Field
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.data.util.CsvHelper
import com.cevichepicante.data.util.StringUtil.removeInvisibleChars
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodRecipe
import com.cevichepicante.model.FoodSource
import javax.inject.Inject

class FoodSourceRepositoryImpl @Inject constructor(
    // TODO api service injected
): FoodSourceRepository {

    @Inject
    lateinit var csvHelper: CsvHelper

    override suspend fun fetchFoodSourceList(context: Context): List<FoodSource> {
        val fetched = csvHelper.readAll(
            context = context,
            csvResId = R.raw.food_sample_data
        )

        /*
            Set data field index list.
         */
        val fieldList = fetched.firstOrNull().orEmpty().let { list ->
            if(list.isNotEmpty()) {
                list.mapIndexed { pos, field ->
                    Field.entries.find { entry ->
                        entry.fieldName == field.removeInvisibleChars()
                    }
                }
            } else {
                Log.d("JSY", "fetched list empty")
                return listOf()
            }
        }

        /*
            Set data of real content list.
         */
        val dataList = fetched.let {
            if(it.size > 1) {
                it.subList(1, it.lastIndex)
            } else {
                Log.d("JSY", "no data")
                return listOf()
            }
        }
        return dataList.map { data ->
            FoodSource(
                serialNo = data.getOrNull(fieldList.indexOf(Field.SerialNo))?: return listOf(),
                recipeTitle = data.getOrNull(fieldList.indexOf(Field.RecipeTitle)),
                cookingName = data.getOrNull(fieldList.indexOf(Field.CookingName)),
                registererId = data.getOrNull(fieldList.indexOf(Field.RegistererId)),
                registererName = data.getOrNull(fieldList.indexOf(Field.RegistererName)),
                viewCount = data.getOrNull(fieldList.indexOf(Field.ViewCount)),
                recommendedCount = data.getOrNull(fieldList.indexOf(Field.RecommendedCount)),
                scrappedCount = data.getOrNull(fieldList.indexOf(Field.ScrappedCount)),
                cookingMethodCategory = data.getOrNull(fieldList.indexOf(Field.CookingMethodCategory)),
                cookingOccasionCategory = data.getOrNull(fieldList.indexOf(Field.CookingOccasionCategory)),
                cookingMaterialCategory = data.getOrNull(fieldList.indexOf(Field.CookingMaterialCategory)),
                cookingKindCategory = data.getOrNull(fieldList.indexOf(Field.CookingKindCategory)),
                cookingIntro = data.getOrNull(fieldList.indexOf(Field.CookingIntro)),
                cookingMaterialContent = data.getOrNull(fieldList.indexOf(Field.CookingMaterialContent)),
                cookingAmount = data.getOrNull(fieldList.indexOf(Field.CookingAmount)),
                cookingLevel = data.getOrNull(fieldList.indexOf(Field.CookingLevel)),
                cookingTime = data.getOrNull(fieldList.indexOf(Field.CookingTime)),
                registeredTime = data.getOrNull(fieldList.indexOf(Field.RegisteredTime))
            )
        }
    }

    override suspend fun fetchFoodList(context: Context): List<Food> {
        val foodSources = fetchFoodSourceList(context)
        return foodSources.map {
            Food(
                id = it.serialNo,
                name = it.cookingName.orEmpty(),
                type = it.cookingKindCategory.orEmpty(),
                servingOccasion = it.cookingOccasionCategory.orEmpty()
            )
        }
    }

    override suspend fun fetchRecipe(
        context: Context,
        foodId: String
    ): FoodRecipe? {
        val foodSources = fetchFoodSourceList(context)
        return foodSources.find {
            it.serialNo == foodId
        }?.let {
            val materialString = it.cookingMaterialContent.orEmpty()
            val ingredientString = materialString
                .substringBefore(context.getString(R.string.string_index_recipe_seasoning))
                .substringAfter(context.getString(R.string.string_index_recipe_ingredients))
            val seasoningString = materialString
                .substringAfter(context.getString(R.string.string_index_recipe_seasoning))

            FoodRecipe(
                id = foodId,
                name = it.cookingName.orEmpty(),
                time = it.cookingTime.orEmpty(),
                foodType = it.cookingKindCategory.orEmpty(),
                level = it.cookingLevel.orEmpty(),
                ingredients = ingredientString.split("|").map { it.trim() },
                seasonings = seasoningString.split("|").map { it.trim() }
            )
        }?: return null
    }
}