package com.fujielectric.ficks.domain.history;

import com.fujielectric.ficks.domain.Document;
import com.fujielectric.ficks.domain.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="download_history")
@Data
public class DownloadHistory extends History {
    @ManyToOne
    @JoinColumn(name="document_id")
    private Document document;

    /** アクセス元 */
    private String referrer;

    /** アクセス元ページ */
    private Integer referrerPage;

    /** アクセス元ページ内順位 */
    private Integer referrerIndex;

    public DownloadHistory() {}
    public DownloadHistory(User user, Document document) {
        super(user, Action.DOWNLOAD);
        setDocument(document);
    }
}
