package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
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

    void addDropDowns(Model model) {
        addCategories(model);
        addAreas(model);
        addPurposes(model);
        addResults(model);
        addReasons(model);
    }

    private void addCategories(Model model) {
        List<Category> categoryList = categoryRepository.findAll(sortByDisplayOrder);
        model.addAttribute("categoryList", categoryList);

        Map<String, String> categories = new TreeMap<>();
        categoryList.forEach(it -> categories.put(it.code, it.name));
        model.addAttribute("categories", categories);
    }

    private void addAreas(Model model) {
        List<Area> areaList = areaRepository.findAll(sortByDisplayOrder);
        model.addAttribute("areaList", areaList);

        Map<Integer, String> areas = new TreeMap<>();
        areaList.forEach(it -> areas.put(it.id, it.name));
        model.addAttribute("areas", areas);

        Map<String, String> areasForString = new TreeMap<>();
        areaList.forEach(it -> areasForString.put(it.id.toString(), it.name));
        model.addAttribute("areasForString", areasForString);
    }

    private void addPurposes(Model model) {
        List<Purpose> purposeList = purposeRepository.findAll(sortByDisplayOrder);
        model.addAttribute("purposeList", purposeList);

        Map<Integer, String> purposes = new TreeMap<>();
        purposeList.forEach(it -> purposes.put(it.id, it.name));
        model.addAttribute("purposes", purposes);

        Map<String, String> purposesForString = new TreeMap<>();
        purposeList.forEach(it -> purposesForString.put(it.id.toString(), it.name));
        model.addAttribute("purposesForString", purposesForString);
    }

    private void addResults(Model model) {
        List<Result> resultList = resultRepository.findAll(sortByDisplayOrder);
        model.addAttribute("resultList", resultList);

        Map<Integer, String> results = new TreeMap<>();
        resultList.forEach(it -> results.put(it.id, it.name));
        model.addAttribute("results", results);

        Map<String, String> resultsForString = new TreeMap<>();
        resultList.forEach(it -> resultsForString.put(it.id.toString(), it.name));
        model.addAttribute("resultsForString", resultsForString);
    }

    private void addReasons(Model model) {
        List<Reason> reasonList = reasonRepository.findAll(sortByDisplayOrder);
        model.addAttribute("reasonList", reasonList);

        Map<Integer, String> reasons = new TreeMap<>();
        reasonList.forEach(it -> reasons.put(it.id, it.name));
        model.addAttribute("reasons", reasons);

        Map<String, String> reasonsForString = new TreeMap<>();
        reasonList.forEach(it -> reasonsForString.put(it.id.toString(), it.name));
        model.addAttribute("reasonsForString", reasonsForString);
    }
}
