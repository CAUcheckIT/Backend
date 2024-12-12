package capstone.checkIT.entity;

import capstone.checkIT.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Month extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false)
    private Member member;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private int days;

    // 한달목표 문장
    @Column
    private String sentence;

    @Column
    private int frequency;

    @Column
    private String productName;

    @Column
    private String productSpace;

    @ElementCollection
    @CollectionTable(name = "week_days", joinColumns = @JoinColumn(name = "month_id"))
    @Column(name = "week_day")
    private List<String> week = new ArrayList<>();

    //별도의 테이블 없이 checkDay 저장
    @ElementCollection
    @CollectionTable(name = "month_check_day", joinColumns = @JoinColumn(name = "month_id"))
    @Column(name = "check_day")
    private List<String> checkDay = new ArrayList<>();

}
