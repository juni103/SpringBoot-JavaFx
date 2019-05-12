package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class JavaFXApplication extends Application {

	private ConfigurableApplicationContext context;
	
	@Value("${app.window.title}") 				String applicationTitle;
	@Value("${classpath:/views/Screen1.fxml}") 	Resource fxmlResource;
	
	@Autowired private Screen1Controller screen1Controller;
	
	@Override
	public void init() throws Exception {
		ApplicationContextInitializer<GenericApplicationContext> initializer =
				new ApplicationContextInitializer<GenericApplicationContext>() {
					
					@Override
					public void initialize(GenericApplicationContext applicationContext) {
						// Register all JavaFX references with Spring
						applicationContext.registerBean(Application.class, 	() -> JavaFXApplication.this);
						applicationContext.registerBean(Parameters.class, 	() -> getParameters());
						applicationContext.registerBean(HostServices.class, () -> getHostServices());
					}
				};
		
		this.context = new SpringApplicationBuilder()
				.sources(JavaFXApplication.class)
				.initializers(initializer)
				.run(getParameters().getRaw().toArray(new String[0])); // Passing command line parameters to SpringBoot
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(fxmlResource.getURL());
			loader.setController(screen1Controller);
			
			Parent root = loader.load();
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle(applicationTitle);
			primaryStage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void stop() throws Exception {
		this.context.stop();
		Platform.exit();
	}

	public static void main(String[] args) {
		Application.launch(JavaFXApplication.class, args);
	}
	
}

