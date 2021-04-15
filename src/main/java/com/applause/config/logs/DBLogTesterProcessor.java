package com.applause.config.logs;

import com.applause.model.Tester;
import org.springframework.batch.item.ItemProcessor;

public class DBLogTesterProcessor implements ItemProcessor<Tester, Tester>
{
    public Tester process(Tester tester) throws Exception
    {
        System.out.println("Inserting tester : " + tester);
        return tester;
    }
}
