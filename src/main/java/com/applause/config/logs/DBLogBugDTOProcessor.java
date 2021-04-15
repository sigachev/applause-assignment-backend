package com.applause.config.logs;

import com.applause.model.batch.BugDTO;
import org.springframework.batch.item.ItemProcessor;

public class DBLogBugDTOProcessor implements ItemProcessor<BugDTO, BugDTO>
{
    public BugDTO process(BugDTO bug) throws Exception
    {
        System.out.println("Inserting bug : " + bug);
        return bug;
    }
}
