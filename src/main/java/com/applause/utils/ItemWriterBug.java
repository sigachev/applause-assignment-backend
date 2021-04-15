package com.applause.utils;

import com.applause.model.Bug;
import com.applause.model.Device;
import com.applause.model.Tester;
import com.applause.repositories.DeviceRepository;
import com.applause.repositories.TesterRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ItemWriterBug implements ItemWriter<Bug> {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    TesterRepository testerRepository;

    @Override
    public void write(List<? extends Bug> bugs) throws Exception {

        for (Bug bug: bugs) {
            Device device = deviceRepository.findById(bug.getId()).get();
            device.getBugs().add(bug);
            deviceRepository.save(device);

            Tester tester = testerRepository.findById(bug.getId()).get();
            tester.getBugs().add(bug);
            testerRepository.save(tester);
        }


    }
}
