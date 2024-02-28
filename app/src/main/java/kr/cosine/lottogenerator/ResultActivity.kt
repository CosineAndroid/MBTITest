package kr.cosine.lottogenerator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import kr.cosine.lottogenerator.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private companion object {
        val resultTypes = listOf(
            listOf("E", "I"),
            listOf("N", "S"),
            listOf("T", "F"),
            listOf("J", "P")
        )
    }

    private val binding: ActivityResultBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val results = intent.getIntegerArrayListExtra("results") ?: arrayListOf()

        var finalResult = ""
        results.indices.forEach {
            finalResult += resultTypes[it][results[it] - 1]
        }
        binding.result.text = finalResult

        val imageResource = resources.getIdentifier(finalResult.lowercase(), "drawable", packageName)
        binding.resultImage.setImageResource(imageResource)

        binding.retry.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}