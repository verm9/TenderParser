package org.verm9.tenderparser;


import org.verm9.tenderparser.model.*;
import org.verm9.tenderparser.view.HtmlView;

import java.io.IOException;

/**
 * Created by nonu on 9/5/2016.
 */
public class Aggregator
{
    public static void main(String[] args)
    {
        HtmlView v = new HtmlView();
        Provider[] providers = new Provider[2];
        providers[0] = new Provider(new FabrikantStrategy());
        providers[1] = new Provider(new EtpfrStrategy());

        Model m = new Model(v, providers);
        Controller c = new Controller(m);
        v.setController(c);
        v.userKeyWordsSelectEmulationMethod();

    }
}
