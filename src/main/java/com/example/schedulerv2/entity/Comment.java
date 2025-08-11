package com.example.schedulerv2.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment(String comment) {
        this.comment = comment;
    }

    public void update(String comment) {
        this.comment = comment;
    }

    public void assignUserAndSchedule(Schedule schedule, User user) {
        this.schedule = schedule;
        this.user = user;
    }
}
