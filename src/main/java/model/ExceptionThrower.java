/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Class with a method used to throw exceptions.
 * 
 * @author Oscar
 */
public class ExceptionThrower {
    
    /**
     *
     * @param msg
     * @throws Exception
     */
    public void throwException(String msg) throws Exception {
        throw new Exception(msg);
    }
    
}
