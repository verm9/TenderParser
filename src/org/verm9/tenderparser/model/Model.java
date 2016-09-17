package org.verm9.tenderparser.model;

import org.verm9.tenderparser.view.View;
import org.verm9.tenderparser.vo.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nonu on 9/9/2016.
 */
public class Model
{
    private View view;
    private Provider[] providers;

    public Model(View view, Provider[] providers) throws IllegalArgumentException
    {
        if (view == null || providers == null || providers.length == 0)
            throw new IllegalArgumentException();
        this.view = view;
        this.providers = providers;
    }

    public void selectKeyWords(String[] keyWords) {
        List<Item> result = new ArrayList<>();
        for (Provider p : providers) {
            result.addAll( p.getTenders(keyWords) );
        }
        view.update(result);
    }

}
