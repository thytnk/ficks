package com.fujielectric.ficks.domain;

import com.fujielectric.ficks.domain.history.History;
import com.fujielectric.ficks.domain.history.SearchHistory;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name="users")
@Data
public class User {

    @Id
    @Column(name = "user_id")
    private Long id;

    //    @Pattern(regexp = "(\\d{6})?", message="半角数字6文字です。")
    private String empNumber;

    private String empName;

    private String role;

    private boolean disabled;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Where(clause = "action='SEARCH'")
    private List<History> searchHistoryList;

    @Transient
    public List<String> getRecentSearchKeywords() {
        return searchHistoryList.stream()
                .sorted((a, b) -> b.getAccessDate().compareTo(a.getAccessDate()))
                .map(h -> (SearchHistory) h)
                .map(h -> h.getKeyword())
                .filter(s -> StringUtils.isNotBlank(s))
                .distinct()
                .limit(5)
                .collect(Collectors.toList());
    }
}
