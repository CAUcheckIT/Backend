package capstone.checkIT.entity;

import capstone.checkIT.entity.common.BaseEntity;
import capstone.checkIT.entity.enums.Role;
import capstone.checkIT.entity.enums.Status;
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
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=100)
    private String name;

    @Column(nullable = false, length = 512)
    private String email;

    @Column
    private String userUrl;

    @Column(nullable =false, length = 512)
    private String address;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length=100)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false" )
    private Boolean isStart;

    @Column
    private Timestamp startTime;

    @Column
    private Timestamp endTime;


    public void encodePassword(String password){
        this.password = password;
    }

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Product> productList=new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Month> monthList = new ArrayList<>(); // 빈 리스트로 초기화

    public void addMonth(Month month) {
        if (this.monthList == null) { // monthList가 null인지 확인
            this.monthList = new ArrayList<>(); // 초기화
        }
        this.monthList.add(month);
        month.setMember(this);
    }

    @OneToMany(mappedBy = "member", cascade=CascadeType.ALL)
    private List<Device> deviceList=new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<TodoAlarm> todoAlarmList=new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MonthAlarm> monthAlarmList=new ArrayList<>();

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL)
    private List<Transport> transportList=new ArrayList<>();

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL)
    private List<Pin> pinList=new ArrayList<>();
}