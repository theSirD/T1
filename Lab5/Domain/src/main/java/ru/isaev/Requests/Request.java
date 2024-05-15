package ru.isaev.Requests;

import jakarta.persistence.*;

@Entity
@Table(name = "Requests")
public class Request {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private RequestStatus status;

    public Request() {
    }

    public Request(RequestStatus status) {
        this.id = id;
        this.status = status;
    }

    public Request(Long id, RequestStatus status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
