package com.fujielectric.ficks.domain.history;

import lombok.Data;

import com.fujielectric.ficks.domain.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="history")
//@DiscriminatorColumn(name="action", discriminatorType=DiscriminatorType.STRING)
@Inheritance(strategy=InheritanceType.JOINED)
@Data
public class History {

    @Id
    @Column(name="history_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "history_history_id_seq")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="history_history_id_seq")
    @SequenceGenerator(name="history_history_id_seq", sequenceName = "history_history_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date accessDate;

    @Enumerated(EnumType.STRING)
    private Action action;

    public History() {
    }
    public History(User user, Action action) {
        setUser(user);
        setAction(action);
    }

    @PrePersist void onPrePersist() {
        setAccessDate(new Date());
    }

}
