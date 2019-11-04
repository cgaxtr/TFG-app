package com.cgaxtr.tfg.view.views;

import com.cgaxtr.tfg.data.model.Question;

import java.util.List;

public interface TestFragmentView {
    void startLoading();
    void stopLoading();
    void showList(List<Question> list);
}
