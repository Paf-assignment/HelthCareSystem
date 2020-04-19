package DTO;

public class Response {
    private  boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Response(boolean status) {
        this.status = status;
    }
}
