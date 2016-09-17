package org.verm9.tenderparser.model;

import org.verm9.tenderparser.vo.Item;

import java.util.List;

/**
 * Created by nonu on 9/5/2016.
 */
public class Provider
{
    private Strategy strategy;

    public Provider(Strategy strategy)
    {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy)
    {
        this.strategy = strategy;
    }

    public List<Item> getTenders(String[] keyWords) {
        return strategy.getItems(keyWords);
    }
}
