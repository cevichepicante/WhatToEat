package com.cevichepicante.database

import com.cevichepicante.database.model.FoodEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FoodDaoTest: DatabaseTest() {

    private val foodListSetFoodType = listOf<FoodEntity>(
        getFoodDataTest(1, "영양식_채식_면", "채식", "면", "영양식"),
        getFoodDataTest(2, "영양식_채식_양식", "채식", "양식", "영양식"),
        getFoodDataTest(3, "영양식_버섯_면", "버섯", "면", "영양식"),
        getFoodDataTest(4, "영양식_버섯_양식", "버섯", "양식", "영양식"),
        getFoodDataTest(5, "일상_채식_면", "채식", "면", "일상"),
        getFoodDataTest(6, "일상_채식_양식", "채식", "양식", "일상"),
        getFoodDataTest(7, "일상_버섯_면", "버섯", "면", "일상"),
        getFoodDataTest(8, "일상_버섯_양식", "버섯", "양식", "일상")
    )

    @Test
    fun getFoodList_material_veggie() = runTest {
        insert(foodListSetFoodType)

        val veggieCategory = "채식"
        val list = foodDao.getFoodListFiltered(
            material = veggieCategory,
            kind = "",
            occasion = ""
        )

        assertEquals(
            list,
            foodListSetFoodType.filter {
                it.cookingMaterialCategory == veggieCategory
            }
        )
    }

    @Test
    fun getFoodList_kind_noodle() = runTest {
        insert(foodListSetFoodType)

        val noodleCategory = "면"
        val list = foodDao.getFoodListFiltered(
            material = "",
            kind = noodleCategory,
            occasion = ""
        )

        assertEquals(
            list,
            foodListSetFoodType.filter {
                it.cookingKindCategory == noodleCategory
            }
        )
    }

    @Test
    fun getFoodList_occasion_casual() = runTest {
        insert(foodListSetFoodType)

        val casualCategory = "일상"
        val list = foodDao.getFoodListFiltered(
            material = "",
            kind = "",
            occasion = casualCategory
        )

        assertEquals(
            list,
            foodListSetFoodType.filter {
                it.cookingOccasionCategory == casualCategory
            }
        )
    }

    private suspend fun insert(list: List<FoodEntity>) {
        foodDao.insertFoodList(list)
    }

    private fun getFoodDataTest(
        id: Int,
        name: String,
        cookingMaterial: String,
        cookingKind: String,
        cookingOccasion: String
    ): FoodEntity {
        return FoodEntity(
            id = id.toString(),
            recipeTitle = "",
            cookingName = name,
            registererId = "",
            registererName = "",
            viewCount = "",
            recommendedCount = "",
            scrappedCount = "",
            cookingMethodCategory = "",
            cookingOccasionCategory = cookingOccasion,
            cookingMaterialCategory = cookingMaterial,
            cookingKindCategory = cookingKind,
            cookingIntro = "",
            cookingMaterialContent = "",
            cookingAmount = "",
            cookingLevel = "",
            cookingTime = "",
            registeredTime = "",
            imageUrl = "",
            price = 10000
        )
    }
}