package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.AnswerActivity;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.util.AnswerUtil;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class BooleanFragment extends BaseFragment implements FragmentValidator {

    @BindView(R.id.text_view)
    TextView textView;

    @BindView(R.id.save_btn)
    Button saveBtn;

    @BindView(R.id.fragment_boolean_yes)
    RadioButton yesRd;

    @BindView(R.id.fragment_boolean_no)
    RadioButton noRd;

    @Inject
    EventBus eventBus;

    private Question question;

    private Answer answer;

    private AnswerActivity activity;

    public BooleanFragment() {
    }

    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        activity = (AnswerActivity) getActivity();

        final Bundle bundle = getArguments();
        question = (Question) bundle.get(SwipeAdapter.QUESTION);

        boolean isLastPage = bundle.getBoolean(SwipeAdapter.LAST_PAGE);
        textView.setText(question.getQuestion());

        if (!isLastPage) {
            saveBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_boolean;
    }

    @OnClick(R.id.save_btn)
    public void onClickSaveBtn() {

        if (!AnswerUtil.wasQuestionAnswered(activity.getAnswers(), question)) {
            Snackbar.make(getView(), getString(R.string.none_answer_was_seleted), Snackbar.LENGTH_SHORT).show();
            return;
        }

        eventBus.post(new ProcessEvent());
    }

    @OnClick(R.id.fragment_boolean_yes)
    public void onClickYes(View view) {

        if (((RadioButton) view).isChecked()) {
            getAnswer(String.valueOf(Boolean.TRUE));
        }
    }

    @OnClick(R.id.fragment_boolean_no)
    public void onClickNo(View view) {

        if (((RadioButton) view).isChecked()) {
            getAnswer(String.valueOf(Boolean.FALSE));
        }
    }

    private void getAnswer(String value) {
        answer = question.getQuestionType().getAnswer();
        answer.setValue(value);
        answer.setQuestion(question);

        eventBus.post(new AnswerEvent(answer));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (question == null || AnswerUtil.wasQuestionAnswered(activity.getAnswers(), question)) {
            return;
        }

        viewPager.setCurrentItem(position);
        Snackbar.make(getView(), getString(R.string.none_answer_was_seleted), Snackbar.LENGTH_SHORT).show();
    }
}
