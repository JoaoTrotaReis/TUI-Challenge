package com.joaoreis.codewars

object TestData {
    val COMPLETED_CHALLENGES = """
        {
  "totalPages": 1,
  "totalItems": 1, 
  "data": [
    {
      "id": "514b92a657cdc65150000006",
      "name": "Multiples of 3 and 5",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript",
        "coffeescript",
        "ruby",
        "javascript",
        "ruby",
        "javascript",
        "ruby",
        "coffeescript",
        "javascript",
        "ruby",
        "coffeescript"
      ]
    }
  ]
}
    """.trimIndent()

    val COMPLETED_CHALLENGES_PAGE_2 = """
        {
  "totalPages": 2,
  "totalItems": 16, 
  "data": [
    {
      "id": "514b92a657cdc65150000006",
      "name": "Challenge 16",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "coffeescript"
      ]
    }
  ]
}
    """.trimIndent()

    val COMPLETED_CHALLENGES_WITH_NEXT_PAGE = """
        {
  "totalPages": 2,
  "totalItems": 15, 
  "data": [
    {
      "id": "514b92a657cdc65150000006",
      "name": "Challenge 1",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000007",
      "name": "Challenge 2",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000008",
      "name": "Challenge 3",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000006",
      "name": "Challenge 4",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000009",
      "name": "Challenge 5",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000000",
      "name": "Challenge 6",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000005",
      "name": "Challenge 7",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000004",
      "name": "Challenge 8",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000003",
      "name": "Challenge 9",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000002",
      "name": "Challenge 10",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000001",
      "name": "Challenge 11",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000016",
      "name": "Challenge 12",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000026",
      "name": "Challenge 13",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000036",
      "name": "Challenge 14",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    },
    {
      "id": "514b92a657cdc65150000046",
      "name": "Challenge 15",
      "slug": "multiples-of-3-and-5",
      "completedAt": "2017-04-06T16:32:09Z",
      "completedLanguages": [ 
        "javascript"
      ]
    }
  ]
}
    """.trimIndent()

    val CHALLENGE_DETAILS = """
        {
          "id": "514b92a657cdc65150000006",
          "name": "Valid Braces",
          "slug": "valid-braces",
          "url": "http://www.codewars.com/kata/valid-braces",
          "category": "algorithms",
          "description": "Write a function called `validBraces` that takes a string ...",
          "tags": ["Algorithms", "Validation", "Logic", "Utilities"],
          "languages": ["javascript", "coffeescript"],
          "rank": {
            "id": -4,
            "name": "4 kyu",
            "color": "blue"
          },
          "createdBy": {
            "username": "xDranik",
            "url": "http://www.codewars.com/users/xDranik"
          },
          "approvedBy": {
            "username": "xDranik",
            "url": "http://www.codewars.com/users/xDranik"
          },
          "totalAttempts": 4911,
          "totalCompleted": 919,
          "totalStars": 12,
          "voteScore": 512,
          "publishedAt": "2013-11-05T00:07:31Z",
          "approvedAt": "2013-12-20T14:53:06Z"
        }
    """.trimIndent()
}