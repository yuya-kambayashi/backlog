package com.bluejeanssystems.backlog.converter;

import com.bluejeanssystems.backlog.model.Milestone;
import com.bluejeanssystems.backlog.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MilestoneConverter implements Converter<String, Milestone> {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Override
    public Milestone convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return milestoneRepository.findById(Long.parseLong(source)).orElse(null);
    }
}
