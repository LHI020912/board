package entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
/*사용 중인 패키지는 Spring Data Relational용이며, JPA(MySQL 연동)를 사용하려면 jakarta.persistence 패키지를 사용
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

*/
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import lombok.Data;

@Data // getter , setter, toString
@Entity
public class Board {

    @Id // 기본키라는 의미
    // 자동 생성설정(SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardId;

    private String title;
    private Integer authorId;

    @Lob // 긴글
    private String content;

    @CreationTimestamp
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate modifiedDate;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int viewCount;
}
