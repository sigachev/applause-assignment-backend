package com.applause.utils.mappers;

import com.applause.model.Bug;
import com.applause.repositories.DeviceRepository;
import com.applause.repositories.TesterRepository;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;


@Component
public class BugFieldSetMapper implements FieldSetMapper<Bug> {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    TesterRepository testerRepository;


    @Override
    public Bug mapFieldSet(FieldSet fieldSet) throws BindException {
       /* Bug bug = new Bug();
        bug.setBugId(fieldSet.readLong("bugId"));

        Device device = deviceRepository.findById(fieldSet.readLong("device")).get();
        device.getBugs().add(bug);
        deviceRepository.save(device);

        Tester tester = testerRepository.findById(fieldSet.readLong("tester")).get();
        tester.getBugs().add(bug);
        testerRepository.save(tester);*/

        return new Bug(fieldSet.readLong("id"),
                deviceRepository.findById(fieldSet.readLong("deviceId")).get(),
                testerRepository.findById(fieldSet.readLong("testerId")).get()
        );
    }
}
