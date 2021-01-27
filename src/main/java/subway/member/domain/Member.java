package subway.member.domain;

public class Member {
    private Long id;
    private String email;
    private String password;
    private int age;

    public Member() {}

    public Member(Long id, String email, String password, Integer age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public Member(String email, String password, Integer age) {
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }

    public Integer getAge() {
        return age;
    }
}
