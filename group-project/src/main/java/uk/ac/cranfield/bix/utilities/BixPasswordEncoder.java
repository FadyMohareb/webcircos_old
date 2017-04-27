/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Encoding password before storing it in database with the others user information. 
 * @author s262012
 */
public class BixPasswordEncoder implements PasswordEncoder {

    private static BixPasswordEncoder instance;

    private BixPasswordEncoder() {

    }

    public static BixPasswordEncoder getInstance() {
        if (instance == null) {
           instance = new BixPasswordEncoder();
        }
        return instance;
    }

    @Override
    public String encode(CharSequence cs) {
        return Utilities.hashPassword(cs.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Utilities.hashPassword(rawPassword.toString()).equals(encodedPassword);
    }

}
