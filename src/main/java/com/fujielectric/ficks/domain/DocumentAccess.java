package com.fujielectric.ficks.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="document_access")
@Data
public class DocumentAccess {

    @Id @Column(name="document_access_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="document_access_id_seq")
    @SequenceGenerator(name="document_access_id_seq", sequenceName = "document_access_document_access_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="document_id")
    private Document document;

    @Temporal(TemporalType.TIMESTAMP)
    private Date accessDate;

    public DocumentAccess() {
    }

    public DocumentAccess(User user, Document document) {
        setUser(user);
        setDocument(document);
    }

    @PrePersist void onPrePersist() {
        setAccessDate(new Date());
    }
}
