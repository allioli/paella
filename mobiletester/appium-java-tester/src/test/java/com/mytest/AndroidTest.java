package com.mytest;

import static org.junit.Assert.assertTrue;

import com.mytest.pages.MainMenu;
import com.mytest.pages.Preferences;

import org.junit.Test;	

public class AndroidTest extends BaseDriver{

    @Test		
    public void atest()throws Exception {		
        
        MainMenu mainMenu = new MainMenu(driver);
        Preferences preferences = new Preferences(driver);

        assertTrue(mainMenu.tapPreferenceButton());

        assertTrue(preferences.tapPreferenceDependenciesButton());

        assertTrue(preferences.checkWifiCheckbox());

        Thread.sleep(4000);
        
    }		

}