package pl.honestit.demos.spring.web.utils;

public interface Pages {

    String HOME = "/home";


    interface Registration {

        String FORM = "/registration/form";
        String SUMMARY = "/registration/summary";
    }

    interface Login {

        String FORM = "/login/form";
    }

    interface User {

        String ACCOUNT = "/user/account";
    }
}
