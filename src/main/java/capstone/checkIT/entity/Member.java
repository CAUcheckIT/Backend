package capstone.checkIT.entity;

import capstone.checkIT.entity.common.BaseEntity;
import capstone.checkIT.entity.enums.Role;
import capstone.checkIT.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

    @Column(nullable =false, length = 512)
    private String address;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, length=100)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Boolean isStart;

    public void encodePassword(String password){
        this.password = password;
    }

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Product> productList=new ArrayList<>();

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