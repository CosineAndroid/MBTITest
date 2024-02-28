package kr.cosine.lottogenerator.data

class QuestionResult {

    val results = mutableListOf<Int>()

    fun addResponse(responses: List<Int>) {
        val count1 = responses.count { it == 1 }
        val count2 = responses.count { it == 2 }
        val result = if (count1 > count2) 1 else 2
        results.add(result)
    }
}