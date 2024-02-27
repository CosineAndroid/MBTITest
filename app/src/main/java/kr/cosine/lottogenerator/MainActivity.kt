package kr.cosine.lottogenerator

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import kr.cosine.lottogenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private lateinit var numberPicker: NumberPicker

    private lateinit var generateButton: Button
    private lateinit var autoGenerateButton: Button
    private lateinit var resetButton: Button

    private lateinit var balls: List<TextView>

    private var isMax = false
    private val pickedNumbers = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        registerBalls()
        registerNumberPicker()
        registerGenerateButton()
        registerAutoGenerateButton()
        registerResetButton()
    }

    private fun registerBalls() {
        balls = listOf(
            binding.lottoBall1, binding.lottoBall2, binding.lottoBall3,
            binding.lottoBall4, binding.lottoBall5, binding.lottoBall6
        )
    }

    private fun registerNumberPicker() {
        numberPicker = binding.numberPicker.apply {
            minValue = 1
            maxValue = 45
        }
    }

    private fun registerGenerateButton() {
        generateButton = binding.lottoGenerate.apply {
            setOnClickListener {
                if (isMax) {
                    showToast("초기화 후에 시도해주세요.")
                    return@setOnClickListener
                }
                val pickedNumber = numberPicker.value
                if (pickedNumbers.contains(pickedNumber)) {
                    showToast("이미 선택된 숫자입니다.")
                    return@setOnClickListener
                }
                setBall(pickedNumbers.size, pickedNumber)
                pickedNumbers.add(pickedNumber)
                if (pickedNumbers.size >= 6) {
                    isMax = true
                }
            }
        }
    }

    private fun setBallBackground(number: Int, ball: TextView) {
        val background = when (number) {
            in 1..10 -> R.drawable.circle_yellow
            in 11..20 -> R.drawable.circle_blue
            in 21..30 -> R.drawable.circle_red
            in 31..40 -> R.drawable.circle_gray
            else -> R.drawable.circle_green
        }
        ball.background = ContextCompat.getDrawable(this, background)
    }

    private fun registerAutoGenerateButton() {
        autoGenerateButton = binding.lottoAutoGenerate.apply {
            setOnClickListener {
                isMax = true
                val numbers = getRandomNumbers()
                numbers.forEachIndexed(::setBall)
            }
        }
    }

    private fun getRandomNumbers(): List<Int> {
        val numbers = (1..45).filter { it !in pickedNumbers }
        return (pickedNumbers + numbers.shuffled().take(6 - pickedNumbers.size)).sorted()
    }

    private fun setBall(index: Int, number: Int) {
        val ball = balls[index]
        ball.isVisible = true
        ball.text = number.toString()
        setBallBackground(number, ball)
    }

    private fun registerResetButton() {
        resetButton = binding.lottoReset.apply {
            setOnClickListener {
                isMax = false
                pickedNumbers.clear()
                balls.forEach {
                    it.isVisible = false
                }
                numberPicker.value = 1
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}