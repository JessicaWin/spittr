package spittr.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Spitter {
	private Long id;

	@NotNull
	@Size(min = 5, max = 16, message = "{username.size}")
	private String username;

	@NotNull
	@Size(min = 5, max = 25, message = "{password.size}")
	private String password;

	@NotNull
	@Size(min = 2, max = 30, message = "{firstName.size}")
	private String firstName;

	@NotNull
	@Size(min = 2, max = 30, message = "{lastName.size}")
	private String lastName;

	@NotNull
	@Email
	private String email;

}
