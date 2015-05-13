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
        List<Category> categoryList = categoryRepository.findAll(sortByDisplayOrder);
        categoryList.forEach(it -> categories.put(it.code, it.name));
        mav.addObject("categories", categories);
        mav.addObject("categoryList", categoryList);

        Map<String, String> areas = new TreeMap<>();
        List<Area> areaList = areaRepository.findAll(sortByDisplayOrder);
        areaList.forEach(it -> areas.put(it.id.toString(), it.name));
        mav.addObject("areas", areas);
        mav.addObject("areaList", areaList);

        Map<String, String> purposes = new TreeMap<>();
        List<Purpose> purposeList = purposeRepository.findAll(sortByDisplayOrder);
        purposeList.forEach(it -> purposes.put(it.id.toString(), it.name));
        mav.addObject("purposes", purposes);
        mav.addObject("purposeList", purposeList);

        Map<Integer, String> results = new TreeMap<>();
        List<Result> resultList = resultRepository.findAll(sortByDisplayOrder);
        resultList.forEach(it -> results.put(it.id, it.name));
        mav.addObject("results", results);
        mav.addObject("resultList", resultList);

        Map<Integer, String> reasons = new TreeMap<>();
        List<Reason> reasonList = reasonRepository.findAll(sortByDisplayOrder);
        reasonList.forEach(it -> reasons.put(it.id, it.name));
        mav.addObject("reasons", reasons);
        mav.addObject("reasonList", reasonList);
    }
}
