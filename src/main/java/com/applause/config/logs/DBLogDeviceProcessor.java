package com.applause.config.logs;

import com.applause.model.Device;
import com.applause.model.Tester;
import org.springframework.batch.item.ItemProcessor;


public class DBLogDeviceProcessor implements ItemProcessor<Device, Device>
{
    public Device process(Device device) throws Exception
    {
        System.out.println("Inserting device : " + device);
        return device;
    }
}

