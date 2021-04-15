package com.applause.utils;

import com.applause.model.Device;
import com.applause.model.Tester;
import com.applause.repositories.DeviceRepository;
import com.applause.repositories.TesterRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.DataBinder;

import java.beans.PropertyEditorSupport;


public class BeanWrapperFieldSetMapperCustomBug<T> extends BeanWrapperFieldSetMapper<T> {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    TesterRepository testerRepository;

    @Override
    protected void initBinder(DataBinder binder) {

        binder.registerCustomEditor(Device.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StringUtils.isNotEmpty(text)) {
                    setValue(deviceRepository.findById(Long.parseLong(text)));
                } else {
                    setValue(null);
                }
            }

        @Override
        public String getAsText() throws IllegalArgumentException {
            Device device = (Device) getValue();
            if (device != null) {
                return device.getId().toString();
            } else {
                return "";
            }
        }
    });

        binder.registerCustomEditor(Tester.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (StringUtils.isNotEmpty(text)) {
                    setValue(testerRepository.findById(Long.parseLong(text)));
                } else {
                    setValue(null);
                }
            }

            @Override
            public String getAsText() throws IllegalArgumentException {
                Tester tester = (Tester) getValue();
                if (tester != null) {
                    return tester.getId().toString();
                } else {
                    return "";
                }
            }
        });

    }
}
