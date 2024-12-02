package capstone.checkIT.entity;

import capstone.checkIT.entity.common.BaseEntity;
import capstone.checkIT.entity.enums.CheckContents;
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
public class Check extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Boolean isChecked;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CheckContents contents;
}
