package kr.cosine.lottogenerator.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import kr.cosine.lottogenerator.R
import kr.cosine.lottogenerator.TestActivity
import kr.cosine.lottogenerator.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    private val binding: FragmentQuestionBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private var page = 0

    private val questionTitles = listOf(
        R.string.question1_title,
        R.string.question2_title,
        R.string.question3_title,
        R.string.question4_title
    )

    private val questions = listOf(
        listOf(R.string.question1_1, R.string.question1_2, R.string.question1_3),
        listOf(R.string.question2_1, R.string.question2_2, R.string.question2_3),
        listOf(R.string.question3_1, R.string.question3_2, R.string.question3_3),
        listOf(R.string.question4_1, R.string.question4_2, R.string.question4_3),
    )

    private val answers = listOf(
        listOf(
            listOf(R.string.question1_1_answer1, R.string.question1_1_answer2),
            listOf(R.string.question1_2_answer1, R.string.question1_2_answer2),
            listOf(R.string.question1_3_answer1, R.string.question1_3_answer2)
        ),
        listOf(
            listOf(R.string.question2_1_answer1, R.string.question2_1_answer2),
            listOf(R.string.question2_2_answer1, R.string.question2_2_answer2),
            listOf(R.string.question2_3_answer1, R.string.question2_3_answer2)
        ),
        listOf(
            listOf(R.string.question3_1_answer1, R.string.question3_1_answer2),
            listOf(R.string.question3_2_answer1, R.string.question3_2_answer2),
            listOf(R.string.question3_3_answer1, R.string.question3_3_answer2)
        ),
        listOf(
            listOf(R.string.question4_1_answer1, R.string.question4_1_answer2),
            listOf(R.string.question4_2_answer1, R.string.question4_2_answer2),
            listOf(R.string.question4_3_answer1, R.string.question4_3_answer2)
        ),
    )

    private var questionTextViews = emptyList<TextView>()
    private var answerRadioGroups = emptyList<RadioGroup>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            page = it.getInt(PAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = binding.root
        binding.apply {
            questionTitle.text = getString(questionTitles[page])
            questionTextViews = listOf(
                questionGroup1.question,
                questionGroup2.question,
                questionGroup3.question
            )
            answerRadioGroups = listOf(
                questionGroup1.answerGroup,
                questionGroup2.answerGroup,
                questionGroup3.answerGroup,
            )
            questionTextViews.forEachIndexed { index, question ->
                question.text = getString(this@QuestionFragment.questions[page][index])
                val answerGroup = answerRadioGroups[index]
                val answer1 = answerGroup.getChildAt(0) as RadioButton
                val answer2 = answerGroup.getChildAt(1) as RadioButton
                answer1.text = getString(answers[page][index][0])
                answer2.text = getString(answers[page][index][1])
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextPage.setOnClickListener {
            val isAllAnswered = answerRadioGroups.all { it.checkedRadioButtonId != -1 }
            if (!isAllAnswered) {
                Toast.makeText(context, "모든 질문에 답해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val responses = answerRadioGroups.map {
                val firstRadioButton = it.getChildAt(0) as RadioButton
                if (firstRadioButton.isChecked) 1 else 2
            }
            val testActivity = activity as? TestActivity ?: return@setOnClickListener
            testActivity.questionResult.addResponse(responses)
            testActivity.showNextPage()
        }
    }

    companion object {
        private const val PAGE = "page"

        fun newInstance(page: Int): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(PAGE, page)
            fragment.arguments = args
            return fragment
        }
    }
}