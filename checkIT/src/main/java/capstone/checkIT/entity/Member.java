package capstone.checkIT.entity;

import capstone.checkIT.entity.common.BaseEntity;
import capstone.checkIT.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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

    @Column(nullable = false)
    private Boolean isStart;

}
