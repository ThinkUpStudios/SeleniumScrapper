package com.thinkup.scrapper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebScrapper {

	public WebDriver driver = new FirefoxDriver();
	public static Logger logger = Logger.getLogger(WebScrapper.class.getName());
	public boolean logueado = false;
	public Random random = new Random();

	public void abrirPaginaInicial(String url) {
		
		driver.navigate().to(url);

	}

		
	public void refrescar() {
		
		driver.navigate().refresh();
//		Alert javascriptconfirm = driver.switchTo().alert();
//		javascriptconfirm.accept();
		
	}
		
	
	public void pruebaElement(){
		WebElement userName_editbox = driver.findElement(By.className("ng-scope"));
		this.logger.log(Level.INFO, userName_editbox.getText());
	}
	
	protected WebElement getElementoByClass(String cssClass) {		
		return driver.findElement(By.className(cssClass));
	}
	
 
	public static void main(String[] args) throws IOException {
		
		final WebScrapper pinnaclesports = new WebScrapper();
		pinnaclesports.abrirPaginaInicial("https://www.pinnaclesports.com/en/odds/today/soccer");
		
		final WebScrapper polla = new WebScrapper();
		polla.abrirPaginaInicial("http://www.polla.cl/Areas/Betting/Template_20_ES/index.html#action=market-group-list&bo-navigation=316.1&boid=316.1");

		final Timer timer = new Timer();
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				
				pinnaclesports.refrescar();
				polla.refrescar();
				try {
					//Espero 10 segundos para refrescar la pantalla, sobre todo porque polla demora mucho en actualizar.
					Thread.sleep(10000);
					
					WebElement elementoPinnac = pinnaclesports.getElementoByClass("ng-scope");
					logger.log(Level.INFO, "###################### Pinnac ######################## /n" + elementoPinnac.getText());
					
					WebElement elemento = polla.getElementoByClass("marketgroupdisplay");
					logger.log(Level.INFO, "###################### Polla ######################## /n" + elemento.getText());	
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
						
			}
			
		};
		
		timer.scheduleAtFixedRate(task, 0, 60000);
	}


	
}