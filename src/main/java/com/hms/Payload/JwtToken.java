package com.hms.Payload;

public class JwtToken {
    private String token;
    private String type;

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken(){
        return token;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }
}
