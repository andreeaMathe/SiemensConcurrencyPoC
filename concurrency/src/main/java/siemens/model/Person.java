package siemens.model;

import java.util.Objects;
import java.util.UUID;

public class Person {

	private UUID id;
	private String name;

	public Person() {

	}

	public Person(UUID id, String name) {
		super();
		this.id = Objects.requireNonNull(id, "Id must not be null");
		this.name = Objects.requireNonNull(name, "Name must not be null");
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = Objects.requireNonNull(id, "Id must not be null");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name, "Name must not be null");
	}

	@Override
	public String toString() {
		return "Person [id=" + id.toString() + ", name=" + name + "]";
	}
}
