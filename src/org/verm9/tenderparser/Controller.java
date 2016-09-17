package org.verm9.tenderparser;

import org.verm9.tenderparser.model.Model;

/**
 * Created by nonu on 9/5/2016.
 */
public class Controller
{
    Model model;

    public Controller(Model model) {
        if (model == null)
            throw new IllegalArgumentException();
        this.model = model;
    }

    public void onKeyWordSelect(String[] keyWords) {
        model.selectKeyWords(keyWords);
    }

}
