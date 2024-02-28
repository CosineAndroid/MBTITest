package kr.cosine.lottogenerator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import kr.cosine.lottogenerator.data.QuestionResult
import kr.cosine.lottogenerator.databinding.ActivityTestBinding
import kr.cosine.lottogenerator.fragment.ViewPagerAdapter

class TestActivity : AppCompatActivity() {

    private val binding: ActivityTestBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private lateinit var viewPager: ViewPager2

    lateinit var questionResult: QuestionResult
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        questionResult = QuestionResult()

        viewPager = binding.viewPager.apply {
            adapter = ViewPagerAdapter(this@TestActivity)
            isUserInputEnabled = false
        }
    }

    fun showNextPage() {
        val currentPage = viewPager.currentItem
        if (currentPage == 3) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("results", ArrayList(questionResult.results))
            startActivity(intent)
            return
        }
        val adapter = viewPager.adapter ?: return
        val nextPage = currentPage + 1
        if (nextPage < adapter.itemCount) {
            viewPager.setCurrentItem(nextPage, true)
        }
    }
}