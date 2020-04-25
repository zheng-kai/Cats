package com.example.a27299.cats.module


data class PicsBean(
        val breeds: List<BreedBean>,//猫的种别
        val categories: List<Category>, //图片类别
        val height: Int,
        val id: String,//图片id
        val url: String,//图片网址
        val width: Int
)

data class BreedBean(
        val adaptability: Int,
        val affection_level: Int,
        val alt_names: String,
        val cfa_url: String,
        val child_friendly: Int,
        val country_code: String,
        val country_codes: String,
        val description: String,
        val dog_friendly: Int,
        val energy_level: Int,
        val experimental: Int,
        val grooming: Int,
        val hairless: Int,
        val health_issues: Int,
        val hypoallergenic: Int,
        val id: String,
        val indoor: Int,
        val intelligence: Int,
        val lap: Int,
        val life_span: String,
        val name: String,
        val natural: Int,
        val origin: String,
        val rare: Int,
        val rex: Int,
        val shedding_level: Int,
        val short_legs: Int,
        val social_needs: Int,
        val stranger_friendly: Int,
        val suppressed_tail: Int,
        val temperament: String,
        val vcahospitals_url: String,
        val vetstreet_url: String,
        val vocalisation: Int,
        val weight: Weight,
        val wikipedia_url: String
)

data class Weight(
        val imperial: String,
        val metric: String
)

data class Category(
        val id: Int,
        val name: String
)

/**
 * example
{
    "breeds": [
        {
            "weight": {
                "imperial": "6 - 13",
                "metric": "3 - 6"
            },
            "id": "buri",
            "name": "Burmilla",
            "cfa_url": "http://cfa.org/Breeds/BreedsAB/Burmilla.aspx",
            "vetstreet_url": "http://www.vetstreet.com/cats/burmilla",
            "temperament": "Easy Going, Friendly, Intelligent, Lively, Playful, Social",
            "origin": "United Kingdom",
            "country_codes": "GB",
            "country_code": "GB",
            "description": "The Burmilla is a fairly placid cat. She tends to be an easy cat to get along with, requiring minimal care. The Burmilla is affectionate and sweet and makes a good companion, the Burmilla is an ideal companion to while away a lonely evening. Loyal, devoted, and affectionate, this cat will stay by its owner, always keeping them company.",
            "life_span": "10 - 15",
            "indoor": 0,
            "lap": 1,
            "alt_names": "",
            "adaptability": 5,
            "affection_level": 5,
            "child_friendly": 4,
            "dog_friendly": 4,
            "energy_level": 3,
            "grooming": 3,
            "health_issues": 3,
            "intelligence": 3,
            "shedding_level": 3,
            "social_needs": 4,
            "stranger_friendly": 3,
            "vocalisation": 5,
            "experimental": 0,
            "hairless": 0,
            "natural": 0,
            "rare": 0,
            "rex": 0,
            "suppressed_tail": 0,
            "short_legs": 0,
            "wikipedia_url": "https://en.wikipedia.org/wiki/Burmilla",
            "hypoallergenic": 0
        }
    ],
    "id": "r530zDuJU",
    "url": "https://cdn2.thecatapi.com/images/r530zDuJU.jpg",
    "width": 3504,
    "height": 2336
}
 */