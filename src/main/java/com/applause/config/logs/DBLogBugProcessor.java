package com.applause.config.logs;

import com.applause.model.Bug;
import org.springframework.batch.item.ItemProcessor;

public class DBLogBugProcessor implements ItemProcessor<Bug, Bug>
{
    public Bug process(Bug bug) throws Exception
    {
        System.out.println("Inserting bug : " + bug);
        return bug;
    }
}
