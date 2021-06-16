package guru.sfg.brewery.domain.security;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.domain.security
 **/
@Accessors(chain = true , fluent = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String role;

	@ManyToMany(mappedBy = "authorities")
	private Set<User> users;
}
