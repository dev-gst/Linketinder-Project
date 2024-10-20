package main.models.dtos.skills

class SkillDTO {
    private final String name

    SkillDTO(String name) {
        Objects.requireNonNull(name, "Name cannot be null")

        this.name = name
    }

    String getName() {
        return name
    }
}
