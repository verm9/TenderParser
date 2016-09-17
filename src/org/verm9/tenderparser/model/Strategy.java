package org.verm9.tenderparser.model;

import org.verm9.tenderparser.vo.Item;

import java.util.List;

/**
 * Created by nonu on 9/5/2016.
 */
public interface Strategy
{
    List<Item> getItems(String[] searchString);
}
