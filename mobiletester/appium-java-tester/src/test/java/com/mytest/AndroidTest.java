package com.mytest;

import com.mytest.pages.MainMenu;

import org.junit.Test;

import static org.junit.Assert.assertTrue;	

public class AndroidTest extends BaseDriver{

    @Test		
    public void atest()throws Exception {		
        
        MainMenu mainMenu = new MainMenu(driver);

        assertTrue(mainMenu.tapPreferenceButton());

        Thread.sleep(3000);
    }		

}