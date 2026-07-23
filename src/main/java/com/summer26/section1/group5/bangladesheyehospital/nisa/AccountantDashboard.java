package com.summer26.section1.group5.bangladesheyehospital.nisa;

public class AccountantDashboard {
    private int accountantId;
    private String name;
    private String email;

    public AccountantDashboard(int accountantId, String email, String name) {
        this.accountantId = accountantId;
        this.email = email;
        this.name = name;
    }

    public int getAccountantId() {
        return accountantId;
    }

    public void setAccountantId(int accountantId) {
        this.accountantId = accountantId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AccountantDashboard{" +
                "accountantId=" + accountantId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
