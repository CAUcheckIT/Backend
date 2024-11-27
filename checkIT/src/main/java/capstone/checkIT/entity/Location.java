package capstone.checkIT.entity;

import capstone.checkIT.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.security.Timestamp;
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
public class Location extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision=10, scale=5)
    private BigDecimal latitude;

    @Column(nullable=false, precision=10, scale=5)
    private BigDecimal longitude;

    @Column(nullable = false)
    private Boolean isFinish;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal velocity;

    @Column
    private Timestamp timestamp;

    @Column
    private Timestamp startTime;

    @OneToMany(mappedBy = "location",cascade = CascadeType.ALL)
    private List<DeviceLocation> deviceLocationList= new ArrayList<>();
}
