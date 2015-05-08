package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class GuiUtils {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private PurposeRepository purposeRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ReasonRepository reasonRepository;

    private Sort sortByDisplayOrder = new Sort(Sort.Direction.ASC, "displayOrder");

    void addDropDowns(ModelAndView mav) {
        Map<String, String> categories = new TreeMap<>();
        categoryRepository.findAll(sortByDisplayOrder).forEach(it -> categories.put(it.code, it.name));
        mav.addObject("categories", categories);

        Map<Integer, String> areas = new TreeMap<>();
        areaRepository.findAll(sortByDisplayOrder).forEach(it -> areas.put(it.id, it.name));
        mav.addObject("areas", areas);

        Map<Integer, String> purposes = new TreeMap<>();
        purposeRepository.findAll(sortByDisplayOrder).forEach(it -> purposes.put(it.id, it.name));
        mav.addObject("purposes", purposes);

        Map<Integer, String> results = new TreeMap<>();
        resultRepository.findAll(sortByDisplayOrder).forEach(it -> results.put(it.id, it.name));
        mav.addObject("results", results);

        Map<Integer, String> reasons = new TreeMap<>();
        reasonRepository.findAll(sortByDisplayOrder).forEach(it -> reasons.put(it.id, it.name));
        mav.addObject("reasons", reasons);
    }
}
