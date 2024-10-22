package cvut.fel.sit.nss.vlak.model.enums;

public enum Role {
    user("ROLE_USER"),
    steward("ROLE_MEMBER"),
    admin("ROLE_ADMIN");
    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
