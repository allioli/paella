package com.mytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.mytest.actions.BasicActionBot;
import com.mytest.actions.IActionBot;
import com.mytest.pages.MainMenu;
import com.mytest.pages.PreferenceDependencies;
import com.mytest.pages.Preferences;

import org.junit.Test;	

public class AndroidTest extends BaseDriver{

    @Test		
    public void testWifiPreferences()throws Exception {		
        
        IActionBot actionBot = new BasicActionBot(driver);

        MainMenu mainMenu = new MainMenu(driver, actionBot);
        assertTrue(mainMenu.validatePage());
        assertTrue(mainMenu.tapPreferenceButton());

        Preferences preferences = new Preferences(driver, actionBot);
        assertTrue(preferences.validatePage());
        assertTrue(preferences.tapPreferenceDependenciesButton());

        PreferenceDependencies preferenceDependencies = new PreferenceDependencies(driver, actionBot);
        assertTrue(preferenceDependencies.validatePage());
        assertTrue(preferenceDependencies.checkWifiCheckbox());
        assertTrue(preferenceDependencies.tapWifiSettingsButton());
        
        String wifiSettings = "SETTINGS WIFI";
        assertTrue(preferenceDependencies.enterWifiSettings(wifiSettings));
        assertTrue(preferenceDependencies.tapWifiSettingsSubmitButton());
        assertTrue(preferenceDependencies.tapWifiSettingsButton());
        assertEquals(wifiSettings, preferenceDependencies.getWifiSettings());

        

        Thread.sleep(4000);
        
    }		

}