package br.com.sw2you.realmeet.domain.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {
    @Id
    @Column(name = "api_key")
    private String apiKey; //chave de acesso para identificar o cliente, usa para ter acesso as funcionalidades

    @Column(name = "description", nullable = false)
    private String description; //nome ou descrição do cliente

    @Column(name = "active", nullable = false)
    private Boolean active;// verificar se esta ativo ou não

    public Client() {}

    private Client(Builder builder) {
        apiKey = builder.apiKey;
        description = builder.description;
        active = builder.active;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return (
                Objects.equals(apiKey, client.apiKey) &&
                        Objects.equals(description, client.description) &&
                        Objects.equals(active, client.active)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey, description, active);
    }

    @Override
    public String toString() {
        return "Client{apiKey='" + apiKey + '\'' + ", description='" + description + '\'' + ", active=" + active + '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String apiKey;
        private String description;
        private Boolean active;

        private Builder() {}

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }
}
