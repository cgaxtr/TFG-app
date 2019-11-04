package com.cgaxtr.tfg.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgaxtr.tfg.R;
import com.cgaxtr.tfg.data.model.Question;
import com.cgaxtr.tfg.presenter.LoginPresenter;
import com.cgaxtr.tfg.presenter.TestPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private List<Question> list;
    private Context context;
    private HashMap<Integer, Integer> responsesSelected;
    TestPresenter presenter;

    public class ItemHolder extends RecyclerView.ViewHolder{
        private TextView question;
        private RadioGroup answers;

        public ItemHolder(View itemView){
            super(itemView);

            this.question = itemView.findViewById(R.id.card_question);
            this.answers = itemView.findViewById(R.id.card_answers);

        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder{
        private Button button;

        public FooterHolder(View itemView) {
            super(itemView);

            this.button = itemView.findViewById(R.id.send);
        }
    }

    public TestAdapter(Context cntx, TestPresenter presenter){
        this.context = cntx;
        this.list = new ArrayList<>();
        responsesSelected = new HashMap<>();
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType){
            case TYPE_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_question, parent, false);
                viewHolder = new ItemHolder(v);
                break;
            case TYPE_FOOTER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_footer_test, parent, false);
                viewHolder = new FooterHolder(v);
                break;
        }

        return viewHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        switch (holder.getItemViewType()){
            case  TYPE_ITEM:
                ItemHolder itemHolder = (ItemHolder) holder;

                final Question question = list.get(position);

                itemHolder.question.setText(question.getQuestion());
                itemHolder.answers.removeAllViews();

                for(Map.Entry<Integer,String> entry : question.getOptions().entrySet()) {
                    int key = entry.getKey();
                    String value = entry.getValue();

                    RadioButton button = new RadioButton(context);
                    button.setText(value);
                    button.setId(key);
                    itemHolder.answers.addView(button);
                }

                if (responsesSelected.containsKey(question.getId())){
                    itemHolder.answers.check(responsesSelected.get(question.getId()));
                }

                itemHolder.answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        responsesSelected.put(question.getId(), radioGroup.getCheckedRadioButtonId());
                    }
                });

                break;
            case TYPE_FOOTER:
                FooterHolder footerHolder = (FooterHolder) holder;
                footerHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (responsesSelected.size() == list.size()){

                            //id question id option
                            TreeMap<Integer, Integer> responses = new TreeMap<>();

                            //first int position, second int option key
                            for(Map.Entry<Integer,Integer> entry : responsesSelected.entrySet()) {
                                Log.d("KEY", entry.getKey().toString());
                                Log.d("VALUE", entry.getValue().toString());
                                int key = list.get(entry.getKey()-1).getId();
                                int value = entry.getValue();

                                responses.put(key, value);

                            }

                            presenter.sendResponses(responses);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size() != 0 ? list.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size()) ? TYPE_FOOTER : TYPE_ITEM;
    }

    public void setList(List<Question> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
