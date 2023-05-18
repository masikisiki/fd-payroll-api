package za.co.firmdev.payroll.data.models;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public final class UserAccountBuilder {
    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String avatarUrl;

    private UserAccountBuilder() {
    }

    public static UserAccountBuilder anUserAccount() {
        return new UserAccountBuilder();
    }

    public UserAccountBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserAccountBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserAccountBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserAccountBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserAccountBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserAccountBuilder withAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public UserAccount build() {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        userAccount.setUserName(userName);
        userAccount.setFirstName(firstName);
        userAccount.setLastName(lastName);
        userAccount.setEmail(email);
        userAccount.setAvatarUrl(avatarUrl);
        return userAccount;
    }


    public UserAccount buildWithGoogleOauthUser(DefaultOAuth2User user) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(user.getName());
        userAccount.setUserName(user.getAttribute("email"));
        userAccount.setFirstName(user.getAttribute("given_name"));
        userAccount.setLastName(user.getAttribute("family_name"));
        userAccount.setEmail(user.getAttribute("email"));
        userAccount.setAvatarUrl(user.getAttribute("picture"));
        return userAccount;
    }

    public UserAccount buildWithGitHubOauthUser(DefaultOAuth2User user) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(user.getName());
        userAccount.setUserName(user.getAttribute("login"));
        userAccount.setAvatarUrl(user.getAttribute("avatar_url"));
        return userAccount;
    }
}
