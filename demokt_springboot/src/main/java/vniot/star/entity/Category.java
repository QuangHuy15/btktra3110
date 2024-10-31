package vniot.star.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "categoriess")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="categoryid")
	private Long id;
	@Column(name="categoryname", columnDefinition = "nvarchar(50)")
	@NotEmpty(message= "Không được mở rộng")
	private String name;
	@Column(name="images", columnDefinition="nvarchar(500)")
	private String image;
	private int status;

}
