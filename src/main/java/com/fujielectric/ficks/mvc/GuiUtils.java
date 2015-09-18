package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.*;
import com.fujielectric.ficks.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    @Autowired
    private LinkRepository linkRepository;

    private Sort sortByDisplayOrder = new Sort(Sort.Direction.ASC, "displayOrder");

    public void addDropDowns(Model model) {
        addCategories(model);
        addAreas(model);
        addPurposes(model);
        addResults(model);
        addReasons(model);
        addLinks(model);
    }

    private void addLinks(Model model) {
        List<Link> links = linkRepository.findAll(sortByDisplayOrder);
        model.addAttribute("links", links);
    }

    private void addCategories(Model model) {
        List<Category> categoryList = categoryRepository.findAll(sortByDisplayOrder);
        model.addAttribute("categoryList", categoryList);

        Map<String, String> categories = new TreeMap<>();
        categoryList.forEach(it -> categories.put(it.getCode(), it.getName()));
        model.addAttribute("categories", categories);
    }

    private void addAreas(Model model) {
        List<Area> areaList = areaRepository.findAll(sortByDisplayOrder);
        model.addAttribute("areaList", areaList);

        Map<Integer, String> areas = new TreeMap<>();
        areaList.forEach(it -> areas.put(it.getId(), it.getName()));
        model.addAttribute("areas", areas);

        Map<String, String> areasForString = new TreeMap<>();
        areaList.forEach(it -> areasForString.put(it.getId().toString(), it.getName()));
        model.addAttribute("areasForString", areasForString);
    }

    private void addPurposes(Model model) {
        List<Purpose> purposeList = purposeRepository.findAll(sortByDisplayOrder);
        model.addAttribute("purposeList", purposeList);

        Map<Integer, String> purposes = new TreeMap<>();
        purposeList.forEach(it -> purposes.put(it.getId(), it.getName()));
        model.addAttribute("purposes", purposes);

        Map<String, String> purposesForString = new TreeMap<>();
        purposeList.forEach(it -> purposesForString.put(it.getId().toString(), it.getName()));
        model.addAttribute("purposesForString", purposesForString);
    }

    private void addResults(Model model) {
        List<Result> resultList = resultRepository.findAll(sortByDisplayOrder);
        model.addAttribute("resultList", resultList);

        Map<Integer, String> results = new TreeMap<>();
        resultList.forEach(it -> results.put(it.getId(), it.getName()));
        model.addAttribute("results", results);

        Map<String, String> resultsForString = new TreeMap<>();
        resultList.forEach(it -> resultsForString.put(it.getId().toString(), it.getName()));
        model.addAttribute("resultsForString", resultsForString);
    }

    private void addReasons(Model model) {
        List<Reason> reasonList = reasonRepository.findAll(sortByDisplayOrder);
        model.addAttribute("reasonList", reasonList);

        Map<Integer, String> reasons = new TreeMap<>();
        reasonList.forEach(it -> reasons.put(it.getId(), it.getName()));
        model.addAttribute("reasons", reasons);

        Map<String, String> reasonsForString = new TreeMap<>();
        reasonList.forEach(it -> reasonsForString.put(it.getId().toString(), it.getName()));
        model.addAttribute("reasonsForString", reasonsForString);
    }
}
