import javax.persistence.*;

@Entity
@Table(name = "passwords")
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "password", nullable = false)
    private String password;

    public Password() {}

    public Password(String serviceName, String password) {
        this.serviceName = serviceName;
        this.password = password;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
