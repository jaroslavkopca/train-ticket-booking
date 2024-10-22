package cvut.fel.sit.nss.vlak.util;

import cvut.fel.sit.nss.vlak.model.enums.Role;

public final class Constants {

    /**
     * Default user role.
     */
    public static final Role DEFAULT_ROLE = Role.user;

    /**
     * Username login form parameter.
     */
    public static final String USERNAME_PARAM = "username";

    private Constants() {
        throw new AssertionError();
    }
}
