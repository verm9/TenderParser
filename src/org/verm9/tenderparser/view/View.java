package org.verm9.tenderparser.view;

import org.verm9.tenderparser.Controller;
import org.verm9.tenderparser.vo.Item;

import java.util.List;

/**
 * Created by nonu on 9/9/2016.
 */
public interface View
{
    void update(List<Item> vacancies);
    void setController(Controller controller);
}
