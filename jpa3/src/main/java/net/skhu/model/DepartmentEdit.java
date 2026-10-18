package net.skhu.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DepartmentEdit {
	int id;
	
	@NotEmpty @NotBlank
	String name;
	
	@NotEmpty @NotBlank
	String shortName;
	
	@NotEmpty @NotBlank
	String phone;
}
