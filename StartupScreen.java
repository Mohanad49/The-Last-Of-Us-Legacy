package view;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Optional;
import java.util.Stack;
import javax.imageio.ImageIO;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.characters.Character;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Group;
import javafx.scene.media.MediaView;

public class StartupScreen extends Application implements EventHandler<MouseEvent>{
	private boolean videoPlayed = false;
	private MediaPlayer mediaPlayer;
	private Stack<Scene> sceneStack = new Stack<>();
    ImageView centerImageView;
	ImageView rightImageView;
	Hero Bat ;
	Stage primary;
	Zombie zombie;
    public void close(Stage primaryStage) {
		boolean answer = confirm.display("Exit","sure u wanna close?");
		if(answer){
		primaryStage.close();
		}
    }
public void start(Stage primaryStage) throws IOException {
		String videoFile = "./New Dead By Daylight Intro _ 2017 (1).mp4";
		Media mediaVideo = new Media(new File(videoFile).toURI().toString());
		MediaPlayer mediaPlayerVideo = new MediaPlayer(mediaVideo);
		MediaView mediaView = new MediaView(mediaPlayerVideo);
		mediaView.fitWidthProperty().bind(primaryStage.widthProperty());
        mediaView.fitHeightProperty().bind(primaryStage.heightProperty());
		Game.heroes.clear();
		Game.availableHeroes.clear();
		
	    File iconFile = new File("./d7es7ls-981ed05e-7d7d-4ebb-b47f-a5864bcbeb65.png");
	    Image icon = new Image(iconFile.toURI().toString());
		primaryStage.getIcons().add(icon);
    	VBox buttonPane = new VBox(10);
    	buttonPane.setAlignment(Pos.CENTER_LEFT);
    	primaryStage.setWidth(1650);
    	primaryStage.setHeight(1000);
    	primaryStage.setResizable(false);
        File backgroundImageFile = new File("./homeScreen.png");
        Image backgroundImage = new Image(backgroundImageFile.toURI().toString());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());                                                                                             
        Button startGameButton = new Button("Start Game");
        startGameButton.setStyle("-fx-background-color: transparent; -fx-font-size: 50px; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold';");
        startGameButton.setMinWidth(200);
        startGameButton.setMinHeight(60);
        startGameButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
        startGameButton.setOnAction(event -> {
				try {
					selectHero(primaryStage,sceneStack);
				} catch (IOException e) {
					 Alert helpAlert = new Alert(AlertType.INFORMATION);
					 	helpAlert.initStyle(StageStyle.UNDECORATED);
		                helpAlert.initOwner(primaryStage);
			            helpAlert.setTitle("Error");
			            helpAlert.setHeaderText(null);
			            helpAlert.setContentText(e.getMessage());
			            helpAlert.showAndWait();
				}
        });
        Button helpButton = new Button("Help");
        helpButton.setStyle("-fx-background-color: transparent; -fx-font-size: 50px; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold';");
        helpButton.setMinWidth(200);
        helpButton.setMinHeight(60);
        helpButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
        helpButton.setOnAction(e -> {
            helpScene(primaryStage,sceneStack);
        });

        Button quitButton = new Button("Quit");
        quitButton.setStyle("-fx-background-color: transparent; -fx-font-size: 50px; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold';");
        quitButton.setMinWidth(200);
        quitButton.setMinHeight(60);
        quitButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
        quitButton.setOnAction(event -> {
     	close(primaryStage); 
       });
        
      
        buttonPane.getChildren().addAll(startGameButton, helpButton, quitButton);
        VBox  vbox = new VBox();
        Label skip = new Label("Recommended Disply Scale Screen : 100%   ");
        skip.setStyle("-fx-background-color: transparent; -fx-font-size: 30; -fx-text-fill: white; -fx-font-family: 'Chiller';");
        Button skipVideoButton = new Button(">Skip intro");
        skipVideoButton.setStyle("-fx-background-color: transparent; -fx-font-size: 40; -fx-text-fill: white; -fx-font-family: 'Chiller';");
        StackPane videoPane = new StackPane();
        vbox.getChildren().addAll(skipVideoButton,skip);
        videoPane.getChildren().addAll(mediaView, vbox);
        StackPane.setMargin(vbox, new Insets(0, 10, 10, 0));
        vbox.setAlignment(Pos.BOTTOM_RIGHT);
        Scene videoScene = new Scene(videoPane, 1600, 925);
        
       StackPane root1 = new StackPane();
       root1.getChildren().addAll(backgroundImageView, buttonPane);
       Scene scene1 = new Scene(root1);

       String audioFile = "./Theme.mp3";
	   Media media = new Media(new File(audioFile).toURI().toString());
	   mediaPlayer = new MediaPlayer(media);
	   mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	   primaryStage.setOnCloseRequest(e ->{
				e.consume(); 
				close(primaryStage);
			});
       if(!videoPlayed) {
    	   videoPlayed = true;
	       primaryStage.setScene(videoScene);
	       mediaPlayerVideo.play();
	       primaryStage.setTitle("The Last Of Us: Legacy");
	       primaryStage.show();
	       mediaPlayerVideo.setOnEndOfMedia(() -> {
	    	    Platform.runLater(() -> {
	    	        primaryStage.setScene(scene1);
	    	 	    mediaPlayer.play();
	    	    });
	    	});
       }
       else {
    	   primaryStage.setScene(scene1);
    	   primaryStage.setTitle("The Last Of Us: Legacy");
	       primaryStage.show();
	       mediaPlayer.play();
	       
       }
       skipVideoButton.setOnAction(event ->{
           mediaPlayerVideo.stop();
           videoPlayed = true;
           Platform.runLater(() -> {
               primaryStage.setScene(scene1);
                mediaPlayer.play();
               });
           });
    }
   
    public void displayMap(Stage primaryStage) {
   	    Button attackButton = new Button("Attack");
   	 attackButton.setPrefSize(205, 30);
   	    attackButton.setStyle("-fx-border-color: black;" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: white; "+
                "-fx-background-color: #013220;" +
                "-fx-font-family: Arial, sans-serif;"+
                "-fx-font-weight: bold;"+
                "-fx-font-size: 14px; ");
   	    attackButton.setOnAction( e-> {   
           	try { 
           		Bat.attack();
           		if(Bat.getTarget().getCurrentHp()==0)
                    Bat.setTarget(null);
   				Game.adjustVisibility(Bat);
   	     	    displayMap(primaryStage);	
   			} catch (NotEnoughActionsException e1) {
   				Alert helpAlert1 = new Alert(AlertType.INFORMATION);
   	            helpAlert1.setTitle("Error");
   	            helpAlert1.setHeaderText(null);
   	            helpAlert1.setContentText(e1.getMessage());
   	            helpAlert1.showAndWait();
   			} catch (InvalidTargetException e1) {
   				Alert helpAlert1 = new Alert(AlertType.INFORMATION);
   	            helpAlert1.setTitle("Error");
   	            helpAlert1.setHeaderText(null);
   	            helpAlert1.setContentText(e1.getMessage());
   	            helpAlert1.showAndWait();
   			}
   });
   	    Button cureButton = new Button("Cure");
   	 cureButton.setPrefSize(205, 30);
   	    cureButton.setStyle("-fx-border-color: black;" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: white; "+
                "-fx-background-color: #013220;" +
                "-fx-font-family: Arial, sans-serif;"+
                "-fx-font-weight: bold;"+
                "-fx-font-size: 14px; ");
   	    cureButton.setOnAction( e1 ->{
   	    	try {
   				Bat.cure();
   				Bat.setTarget(null);
   				 for(Hero hero : Game.heroes)
   					 Game.adjustVisibility(hero);
   				 	displayMap(primaryStage);
   	     	     
   			} catch (NoAvailableResourcesException e) {
   				Alert helpAlert1 = new Alert(AlertType.INFORMATION);
   	            helpAlert1.setTitle("Error");
   	            helpAlert1.setHeaderText(null);
   	            helpAlert1.setContentText(e.getMessage());
   	            helpAlert1.showAndWait();
   				
   			} catch (InvalidTargetException e) {
   				Alert helpAlert1 = new Alert(AlertType.INFORMATION);
   	            helpAlert1.setTitle("Error");
   	            helpAlert1.setHeaderText(null);
   	            helpAlert1.setContentText(e.getMessage());
   	            helpAlert1.showAndWait();
   				
   			} catch (NotEnoughActionsException e) {
   				Alert helpAlert1 = new Alert(AlertType.INFORMATION);
   	            helpAlert1.setTitle("Error");
   	            helpAlert1.setHeaderText(null);
   	            helpAlert1.setContentText(e.getMessage());
   	            helpAlert1.showAndWait();	
   			}
   	    });
   	    Button useSpecialButton = new Button("Use Special");
   	 useSpecialButton.setPrefSize(205,30);
   	    useSpecialButton.setStyle("-fx-border-color: black;" +
                "-fx-border-width: 1px;" +
                "-fx-text-fill: white; "+
                "-fx-background-color: #013220;" +
                "-fx-font-family: Arial, sans-serif;"+
                "-fx-font-weight: bold;"+
                "-fx-font-size: 14px; ");
   	    useSpecialButton.setOnAction( e1 -> {
   	    	try {
   				Bat.useSpecial();
   				  displayMap(primaryStage);
   			} catch (NoAvailableResourcesException e) {
   				Alert helpAlert1 = new Alert(AlertType.INFORMATION);
   	            helpAlert1.setTitle("Error");
   	            helpAlert1.setHeaderText(null);
   	            helpAlert1.setContentText(e.getMessage());
   	            helpAlert1.showAndWait();	
   			} catch (InvalidTargetException e) {
   				Alert helpAlert1 = new Alert(AlertType.INFORMATION);
   	            helpAlert1.setTitle("Error");
   	            helpAlert1.setHeaderText(null);
   	            helpAlert1.setContentText(e.getMessage());
   	            helpAlert1.showAndWait();	
   			}
   	    });
   	    Button up = new Button("  UP  ");
       	up.setStyle("-fx-font-size: 14px; " +
                   "-fx-padding: 8px 16px; " +
                   "-fx-background-color: #027148; " +
                   "-fx-text-fill: white; " +
                   "-fx-font-weight: bold;");

       	up.setOnAction(e -> {
       	    try {
       	    	int h1=Bat.getCurrentHp();
       	        Bat.move(Direction.UP);
       	        int h2=Bat.getCurrentHp();
       	        if(h2<h1)
       	        	Trapped(Bat);
       	    } catch (MovementException e1) {
       	    	Alert helpAlert2 = new Alert(AlertType.INFORMATION);
		            helpAlert2.setTitle("Error");
		            helpAlert2.setHeaderText(null);
		            helpAlert2.setContentText(e1.getMessage());
		            helpAlert2.showAndWait();
       	    } catch (NotEnoughActionsException e1) {
       	    	Alert helpAlert2 = new Alert(AlertType.INFORMATION);
		            helpAlert2.setTitle("Error");
		            helpAlert2.setHeaderText(null);
		            helpAlert2.setContentText(e1.getMessage());
		            helpAlert2.showAndWait();
       	    }
       	    displayMap(primaryStage);
       	});
       	Button left = new Button("LEFT");
       	left.setStyle("-fx-font-size: 14px; " +
                   "-fx-padding: 8px 16px; " +
                   "-fx-background-color: #027148; " +
                   "-fx-text-fill: white; " +
                   "-fx-font-weight: bold;");

       	left.setOnAction(e -> {
       	    try {
       	    	int h1=Bat.getCurrentHp();
       	        Bat.move(Direction.LEFT);
       	        int h2=Bat.getCurrentHp();
       	        if(h2<h1)
       	        	Trapped(Bat);
       	    } catch (MovementException e1) {
       	    	Alert helpAlert2 = new Alert(AlertType.INFORMATION);
		            helpAlert2.setTitle("Error");
		            helpAlert2.setHeaderText(null);
		            helpAlert2.setContentText(e1.getMessage());
		            helpAlert2.showAndWait();
       	    } catch (NotEnoughActionsException e1) {
       	    	Alert helpAlert2 = new Alert(AlertType.INFORMATION);
		            helpAlert2.setTitle("Error");
		            helpAlert2.setHeaderText(null);
		            helpAlert2.setContentText(e1.getMessage());
		            helpAlert2.showAndWait();
       	    }
       	    displayMap(primaryStage);
       	});
       	Button right = new Button("RIGHT");
       	right.setStyle("-fx-font-size: 14px; " +
                   "-fx-padding: 8px 16px; " +
                   "-fx-background-color: #027148; " +
                   "-fx-text-fill: white; " +
                   "-fx-font-weight: bold;");

       	right.setOnAction(e -> {
       	    try {
       	    	int h1=Bat.getCurrentHp();
       	        Bat.move(Direction.RIGHT);
       	        int h2=Bat.getCurrentHp();
       	        if(h2<h1)
       	        	Trapped(Bat);
       	    } catch (MovementException e1) {
       	    	Alert helpAlert2 = new Alert(AlertType.INFORMATION);
		            helpAlert2.setTitle("Error");
		            helpAlert2.setHeaderText(null);
		            helpAlert2.setContentText(e1.getMessage());
		            helpAlert2.showAndWait();
       	    } catch (NotEnoughActionsException e1) {
       	    	Alert helpAlert2 = new Alert(AlertType.INFORMATION);
		            helpAlert2.setTitle("Error");
		            helpAlert2.setHeaderText(null);
		            helpAlert2.setContentText(e1.getMessage());
		            helpAlert2.showAndWait();
       	    }
       	    displayMap(primaryStage);
       	});
       	Button down = new Button("DOWN");
       	down.setStyle("-fx-font-size: 14px; " +
                   "-fx-padding: 8px 16px; " +
                   "-fx-background-color: #027148; " +
                   "-fx-text-fill: white; " +
                   "-fx-font-weight: bold;");

       	down.setOnAction(e -> {
       	    try {
       	    	int h1=Bat.getCurrentHp();
       	        Bat.move(Direction.DOWN);
       	        int h2=Bat.getCurrentHp();
       	        if(h2<h1)
       	        	Trapped(Bat);
       	    } catch (MovementException e1) {
       	    	Alert helpAlert2 = new Alert(AlertType.INFORMATION);
		            helpAlert2.setTitle("Error");
		            helpAlert2.setHeaderText(null);
		            helpAlert2.setContentText(e1.getMessage());
		            helpAlert2.showAndWait();
       	    } catch (NotEnoughActionsException e1) {
       	    	Alert helpAlert2 = new Alert(AlertType.INFORMATION);
		            helpAlert2.setTitle("Error");
		            helpAlert2.setHeaderText(null);
		            helpAlert2.setContentText(e1.getMessage());
		            helpAlert2.showAndWait();
       	    }
       	    displayMap(primaryStage);
       	});
       	HBox buttonBox = new HBox();
       	buttonBox.setAlignment(Pos.CENTER);
       	buttonBox.getChildren().addAll(left,right);
       	VBox vbox1 = new VBox();
       	vbox1.getChildren().addAll(up,buttonBox,down);
       	vbox1.setAlignment(Pos.CENTER);
       	
       	VBox action = new VBox();
       	action.getChildren().addAll(attackButton,useSpecialButton,cureButton);
       	VBox vbox2 = new VBox(20);
       	vbox2.getChildren().addAll(action,vbox1);
       	vbox2.setAlignment(Pos.BASELINE_CENTER);
    	primaryStage.setTitle("The Last of Us: Legacy");
    	primaryStage.setWidth(1650);
    	primaryStage.setHeight(1000);
    	primaryStage.setResizable(false);
    	File backgroundImageFile = new File("./wallpaper.png");
    	Image backgroundImage2 = new Image(backgroundImageFile.toURI().toString());
        GridPane mapGrid = new GridPane();
        mapGrid.setStyle("-fx-border-color: black;" +
                "-fx-border-width: 1px;");
        
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {     	
                Button cellButton = new Button();
                File backgroundImageFile3 = new File("./latest.22png");
                Image visibleImage = new Image(backgroundImageFile3.toURI().toString());
                cellButton.setPrefWidth(70);
                cellButton.setPrefHeight(49);
                cellButton.setStyle("-fx-border-color: black"); 
                if (Game.map[14 - row][col].isVisible()) {
                	if((Game.map[14-row][col] instanceof CharacterCell && ((CharacterCell)Game.map[14-row][col]).getCharacter()==null) || Game.map[14-row][col] instanceof TrapCell) {
                	 BackgroundImage backgroundImage = new BackgroundImage(
                        visibleImage,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(70, 49, false, false, false, false));
                	 cellButton.setBackground(new Background(backgroundImage));}
                	 else {
                		 if(Game.map[14-row][col] instanceof CharacterCell) {
                			 Character h=((CharacterCell)Game.map[14-row][col]).getCharacter();
                			 String y = null;
                			 switch (h.getName()){
             				case "Joel Miller":
             					y= "./418a4cb33049217f82b65a9059f77b46.png";
             					cellButton.setOnAction(e -> {           		              
             		                Bat.setTarget((Hero) h);
             		               displayMap(primaryStage);
             		        		 });
             					break;
             				case "Ellie Williams":
             					y="./ellie_large.png";
             					cellButton.setOnAction(e -> {           		              
             						Bat.setTarget((Hero) h);
             		               displayMap(primaryStage);
             		        		 });
             					break;
             				case "Tess":
             					y="./250.png";
             					cellButton.setOnAction(e -> {           		              
             		                Bat.setTarget((Hero) h);
             		               displayMap(primaryStage);
             		        		 });
             					break;
             				case "Riley Abel":
             					y="./latest.png";
             					cellButton.setOnAction(e -> {           		              
             						Bat.setTarget((Hero) h);
             		               displayMap(primaryStage);
             		        		 });
             					break;
             				case "Tommy Miller":
             					y="./9kk.png";
             					cellButton.setOnAction(e -> {           		              
             						Bat.setTarget((Hero) h);
             		               displayMap(primaryStage);
             		        		 });
             					break;
             				case "Bill":
             					y="./9k.png";
             					cellButton.setOnAction(e -> {           		              
             						Bat.setTarget((Hero) h);
             		               displayMap(primaryStage);
             		        		 });
             					break;
             				case "David":
             					y="./Terry-Crews-Expendables-750x400.jpg";
             					cellButton.setOnAction(e -> {           		              
             						Bat.setTarget((Hero) h);
             		               displayMap(primaryStage);
             		        		 });
             					break;
             				case "Henry Burell":
             					y="./images.jpg";
             					cellButton.setOnAction(e -> {           		              
             						Bat.setTarget((Hero) h);
             		                displayMap(primaryStage);
             		        		 });
             					break;
             				default: y="./188.png";cellButton.setOnAction(e -> {  
         		                Bat.setTarget((Zombie) h);
         		                displayMap(primaryStage);
             				});
             				}				
                				
                				File backgroundImageFile1 = new File(y);
                	    	    Image image = new Image(backgroundImageFile1.toURI().toString());
                		        BackgroundImage backgroundImage = new BackgroundImage(
                                        image,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.DEFAULT,
                                        new BackgroundSize(70, 49,  false, false, false, false));
                                	 	cellButton.setBackground(new Background(backgroundImage)); 
                		 }
                	else {
                		if(Game.map[14-row][col] instanceof CollectibleCell) {
                			if(((CollectibleCell)Game.map[14-row][col]).getCollectible() instanceof Supply) {
                				 File backgroundImageFile1 = new File("./supply.png");
                	                Image visibleImage1 = new Image(backgroundImageFile1.toURI().toString());
                	                BackgroundImage backgroundImage = new BackgroundImage(
                	                        visibleImage1,
                	                        BackgroundRepeat.NO_REPEAT,
                	                        BackgroundRepeat.NO_REPEAT,
                	                        BackgroundPosition.DEFAULT,
                	                        new BackgroundSize(70, 49,  false, false, false, false));
                	                	 cellButton.setBackground(new Background(backgroundImage));}
                			else {
                				File backgroundImageFile1 = new File("./vaccine.png");
            	                Image visibleImage1 = new Image(backgroundImageFile1.toURI().toString());
            	                BackgroundImage backgroundImage = new BackgroundImage(
            	                        visibleImage1,
            	                        BackgroundRepeat.NO_REPEAT,
            	                        BackgroundRepeat.NO_REPEAT,
            	                        BackgroundPosition.DEFAULT,
            	                        new BackgroundSize(70, 49,  false, false, false, false));
            	                	 cellButton.setBackground(new Background(backgroundImage));}
                			}
                			}
                		}
                	 }
                else {
                	File backgroundImageFile1 = new File("./Night (2).png");
	                Image visibleImage1 = new Image(backgroundImageFile1.toURI().toString());
	                BackgroundImage backgroundImage = new BackgroundImage(
	                        visibleImage1,
	                        BackgroundRepeat.NO_REPEAT,
	                        BackgroundRepeat.NO_REPEAT,
	                        BackgroundPosition.DEFAULT,
	                        new BackgroundSize(70, 49,  false, false, false, false));
	                	 cellButton.setBackground(new Background(backgroundImage));
                }
                mapGrid.add(cellButton, col, row);

                int finalRow = 14 - row;
                int finalCol = col;
                if(Game.map[14-row][col].isVisible()) {
                    if(Game.map[14-row][col] instanceof CharacterCell) {
                    Character h=((CharacterCell)Game.map[finalRow][finalCol]).getCharacter();
                    if(h != null) {
                    	switch (h.getName()){
        				case "Joel Miller":
        					cellButton.setOnAction(e -> {           		              
        						Bat.setTarget((Hero) h);
        						if(Bat.getTarget()==null)
        		                    Bat.setTarget(null);
        		                displayMap(primaryStage);
        		        		 });
        					break;
        				case "Ellie Williams":
        					
        					cellButton.setOnAction(e -> {    
        						Bat.setTarget((Hero) h);
        						if(Bat.getTarget()==null)
        		                    Bat.setTarget(null);
        		                displayMap(primaryStage);
        		        		 });
        					break;
        				case "Tess":
        					
        					cellButton.setOnAction(e -> {           
        						Bat.setTarget((Hero) h);
        						if(Bat.getTarget()==null)
        		                    Bat.setTarget(null);
        		                displayMap(primaryStage);
        		            });
        					break;
        				case "Riley Abel":
        					
        					cellButton.setOnAction(e -> {
        						Bat.setTarget((Hero) h);
        						if(Bat.getTarget()==null)
        		                    Bat.setTarget(null);
        		                displayMap(primaryStage);
        		            });
        					break;
        				case "Tommy Miller":
        					
        					cellButton.setOnAction(e -> {
        						Bat.setTarget((Hero) h);
        						if(Bat.getTarget()==null)
        		                    Bat.setTarget(null);
        		                displayMap(primaryStage);
        		            });
        					break;
        				case "Bill":
        					cellButton.setOnAction(e -> {
        						Bat.setTarget((Hero) h);
        						if(Bat.getTarget()==null)
        		                    Bat.setTarget(null);
        		                displayMap(primaryStage);
        		            });
        					break;
        				case "David":
        					cellButton.setOnAction(e -> {
        						Bat.setTarget((Hero) h);
        						if(Bat.getTarget()==null)
        		                    Bat.setTarget(null);
        		                displayMap(primaryStage);
        		            });
        					break;
        				case "Henry Burell":
        					cellButton.setOnAction(e -> {
        						Bat.setTarget((Hero) h);
        						if(Bat.getTarget()==null)
        		                    Bat.setTarget(null);
        		                displayMap(primaryStage);
        		            });}}}
                }
                
            }
        } if(Game.checkWin()) {
        	gameWinScene(primaryStage);
        }
        else if(Game.checkGameOver()) {
        	gameOverScene(primaryStage);
        }
       Button endTurnButton = new Button("End Turn");
       endTurnButton.setPrefSize(100, 10);
       endTurnButton.setOnAction(e -> {
    	   try {
			Game.endTurn();
			for(Hero hero:Game.heroes) 
				Game.adjustVisibility(hero);
    	   	} catch (NotEnoughActionsException e1) {
    	   	 Alert helpAlert = new Alert(AlertType.INFORMATION);
	            helpAlert.setTitle("Error");
	            helpAlert.setHeaderText(null);
	            helpAlert.setContentText(e1.getMessage());
	            helpAlert.showAndWait();
	            helpAlert.initStyle(StageStyle.UNDECORATED);
                helpAlert.initOwner(primaryStage);
    	   	} catch (InvalidTargetException e1) {
    	   	 Alert helpAlert = new Alert(AlertType.INFORMATION);
	            helpAlert.setTitle("Error");
	            helpAlert.setHeaderText(null);
	            helpAlert.setContentText(e1.getMessage());
	            helpAlert.showAndWait();
	            helpAlert.initStyle(StageStyle.UNDECORATED);
                helpAlert.initOwner(primaryStage);
    	   	}
    	   catch(ConcurrentModificationException e1) {
    		   for(Hero hero:Game.heroes) 
   				Game.adjustVisibility(hero);
    	   }
    	   	displayMap(primaryStage);
            });
       	VBox map = new VBox();
        endTurnButton.setPrefWidth(1050);
        endTurnButton.setPrefHeight(10);
        endTurnButton.setStyle("-fx-background-color:#0D2737 ; -fx-font-size: 30px; -fx-text-fill: white; -fx-font-family: 'Abade';");
        map.getChildren().addAll(mapGrid,endTurnButton);
        BorderPane boarderpane =new BorderPane();
        Button backButton = new Button(">Back");
        backButton.setStyle("-fx-background-color: transparent; -fx-font-size: 40; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold';");
        backButton.setMinWidth(200);
        backButton.setMinHeight(60);
        backButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
        backButton.setOnAction(event -> {
        	Scene previousScene = sceneStack.pop();
            primaryStage.setScene(previousScene);
            Game.heroes.clear();
            Game.availableHeroes.clear();
           
        });
        HBox topLeftBox = new HBox(10);  
        topLeftBox.getChildren().add(backButton);
        VBox vbox3 = new VBox(490);
        vbox3.getChildren().addAll(vbox2,topLeftBox);
       
        boarderpane.setCenter(map);
        boarderpane.setRight(vbox3);
        vbox3.setAlignment(Pos.CENTER);
        VBox healthbar = healthbar(primaryStage); 
        boarderpane.setLeft(healthbar);
         Pane root = new Pane();
         BackgroundImage background = new BackgroundImage(
                 backgroundImage2,
                 BackgroundRepeat.NO_REPEAT,
                 BackgroundRepeat.NO_REPEAT,
                 null,
                 new BackgroundSize(
                     1.0, 1.0, true, true, false, false
                 )
             );
        root.setBackground(new Background(background));
        root.getChildren().add(boarderpane);
        Scene mapScene = new Scene(root);
        primaryStage.setScene(mapScene);
    } 
    public void confirm3(Stage primaryStage,Alert helpAlert,Character target,Scene old) {
    	ButtonType confirm=new ButtonType("Confim Target",ButtonData.OK_DONE);
    	ButtonType cancel=new ButtonType("Cancel",ButtonData.OK_DONE);
    	helpAlert.getButtonTypes().setAll(confirm,cancel);
    	helpAlert.initStyle(StageStyle.UNDECORATED);
        helpAlert.initOwner(primaryStage);
    	Optional<ButtonType> result = helpAlert.showAndWait();
        if (result.isPresent()) {
         ButtonType buttonClicked = result.get();
         if(buttonClicked==confirm) {
        	 Bat.setTarget(target);
     	     displayMap(primaryStage);	
         }
         if(buttonClicked==cancel) {
        	 helpAlert.close();
         }
        }	
    }
   
    public static void main(String[] args) {
        launch(args);
    }
	public static void Trapped(Hero h) {
		 Alert helpAlert = new Alert(AlertType.INFORMATION);
         helpAlert.setTitle("Trapped");
         helpAlert.setHeaderText(null);
         helpAlert.setContentText(" You have been caught in a trap \n Health has been reduced to: " + h.getCurrentHp());
         helpAlert.showAndWait();
	}
	public VBox healthbar(Stage primaryStage) {
    	String y = " "; 
    	String x = " ";
    	VBox vbox = new VBox();
    	ArrayList<Hero> heroesCopy = new ArrayList<>(Game.heroes);
    	for(Hero hero : heroesCopy) {
    		int CurrHelth = hero.getCurrentHp();
    		int MaxHealth = hero.getMaxHp();
    		int actionpoint = hero.getActionsAvailable();
    		switch (hero.getName()){
    		case "Joel Miller":
    			y= "./418a4cb33049217f82b65a9059f77b46.png";
    			x = "Fighter";break;
    		case "Ellie Williams":
    			y="./ellie_large.png";
    			x = "Medic";
    			break;
    		case "Tess":
    			y="./250.png";
    			x = "Explorer";
    			break;
    		case "Riley Abel":
    			y="./latest.png";
    			x = "Explorer";
    			break;
    		case "Tommy Miller":
    			y="./9kk.png";
    			x = "Explorer";
    			break;
    		case "Bill":
    			y="./9k.png";
    			x = "Medic";
    			break;
    		case "David":
    			y="./Terry-Crews-Expendables-750x400.jpg";  
    			x = "Fighter";
    			break;
    		case "Henry Burell":
    			y="./images.jpg";
    			x = "Medic";
    			break;
    		}
    		VBox vbox2 = new VBox();
        	HBox hbox = new HBox();	
    		Label actions = new Label("Actions Available : "+actionpoint);
    		Label name = new Label("Name : "+hero.getName());
    		Label hp = new Label("Health : "+hero.getCurrentHp());
    		Label attack=new Label("Attack Dmg: "+hero.getAttackDmg());
    		Label vc = new Label("Vaccine Inventory : "+hero.getVaccineInventory().size());
    		Label sc = new Label("Supply Inventory : "+hero.getSupplyInventory().size());
    		Label type = new Label("Type : "+ x);
    		Label actions2 = new Label("Max Actions per Turn : "+ hero.getMaxActions());
    		Label target;
    		if(hero.getTarget()==null) 
    			 target = new Label("Target not selected");
    		else
    			target = new Label("Target : "+ hero.getTarget().getName());
    		VBox vBox = new VBox();
    		HBox hBox = new HBox();
    		name.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: Agency FB;");
    		type.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: Agency FB;");
    		actions.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: Agency FB;");
    		target.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: Agency FB;");
    		actions2.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: Agency FB;");
    		hp.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: Agency FB;");
    		vc.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: Agency FB;");
    		sc.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: Agency FB;");
    		attack.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-font-family: Agency FB;");
    		Rectangle background = new Rectangle(200, 10);
            background.setStyle("-fx-fill: lightgray;");
            Rectangle background1 = new Rectangle(200, 10);
            background1.setStyle("-fx-fill: lightgray;");
            Rectangle healthLevel = new Rectangle((double) CurrHelth / MaxHealth * 200, 10);
            healthLevel.setStyle("-fx-fill: green;");
            if( CurrHelth  <= 40) {
            healthLevel.setStyle("-fx-fill: red;");
            }

            
            hBox.getChildren().add(healthLevel);
            vBox.getChildren().addAll(background1,hBox,background);
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(vBox);
            
            File backgroundImageFile = new File(y);
            Image backgroundImage = new Image(backgroundImageFile.toURI().toString());

            Button cellButton = new Button();
            cellButton.setPrefWidth(167);
            cellButton.setPrefHeight(190);

            File backgroundImageFile1 = new File(y);
            Image visibleImage1 = new Image(backgroundImageFile1.toURI().toString());
            BackgroundImage backgroundImage1 = new BackgroundImage(
                    visibleImage1,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(167, 190,  false, false, false, false));
            	 cellButton.setBackground(new Background(backgroundImage1));

            cellButton.setOnAction(e -> {            	
                Bat = hero;
                if(Bat.getTarget()==null)
                    Bat.setTarget(null);
                displayMap(primary);
            });


            vbox2.getChildren().addAll(name,type,hp,attack,actions2,actions,target,sc,vc,vBox);
            
            vbox2.setAlignment(Pos.CENTER);
            hbox.getChildren().addAll(cellButton, vbox2);
            vbox.getChildren().add(hbox); 
            if(hero.getName().equals(Bat.getName())) {
            	 hbox.setStyle("-fx-border-color: yellow;" +
                         "-fx-border-width: 3px;");
            }
    	}
    	return vbox;
    }
	
	public void gameOverScene(Stage primaryStage) {
		primaryStage.setWidth(1650);
        primaryStage.setHeight(1000);
        primaryStage.setResizable(true);
    	Stage gameOverStage = new Stage();
    	gameOverStage.setHeight(500);
    	gameOverStage.setWidth(600);
    	gameOverStage.setResizable(false);
    	File backgroundImageFile = new File("./gameover.png");
    	Image backgroundImage2 = new Image(backgroundImageFile.toURI().toString());
    	 Pane root = new Pane();
         BackgroundImage background = new BackgroundImage(
                 backgroundImage2,
                 BackgroundRepeat.NO_REPEAT,
                 BackgroundRepeat.NO_REPEAT,
                 null,
                 new BackgroundSize(
                     1.0, 1.0, true, true, false, false
                 )
             );
        root.setBackground(new Background(background));
    	Label label = new Label("Game Over!");
    	label.setStyle("-fx-font-size: 16px; -fx-text-fill: red; -fx-font-weight: bold;");
    	HBox hbox = new HBox(20);
    	VBox vbox = new VBox(300);
    	Button useless = new Button("");
    	
    	 Button tryAgainButton = new Button("Main Menu");
    	 tryAgainButton.setStyle("-fx-background-color: transparent; -fx-font-size: 50px; -fx-text-fill: red; -fx-font-family: 'Abadi';");
    	 tryAgainButton.setMinWidth(100);
    	 tryAgainButton.setMinHeight(20);
    	 tryAgainButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
         tryAgainButton.setOnAction(event -> {
        	 gameOverStage.close();
        	 primaryStage.close();
        	 Platform.runLater(() -> {
                 try {
                     start(new Stage());
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             });
         });
             
         Button quitButton = new Button("Quit");
         quitButton.setStyle("-fx-background-color: transparent; -fx-font-size: 50px; -fx-text-fill: red; -fx-font-family: 'Abadi';");
         quitButton.setMinWidth(100);
         quitButton.setMinHeight(20);
         quitButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
         quitButton.setOnAction(event -> {
        	 Platform.exit();
        	 System.exit(0);
        });
         
        hbox.getChildren().addAll(tryAgainButton,quitButton);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.getChildren().addAll(useless,hbox);
        root.getChildren().add(vbox);
        
        Scene scene = new Scene(root);
        gameOverStage.setScene(scene);

       
        gameOverStage.show();
    }
    
    public void gameWinScene(Stage primaryStage) {
    	VBox vbox = new VBox(300);
    	primaryStage.setWidth(1650);
        primaryStage.setHeight(1300);
    	primaryStage.setResizable(true);
    	Stage stage = new Stage();
    	Label label = new Label("");
    	label.setStyle("-fx-font-size: 16px; -fx-text-fill: red; -fx-font-weight: bold;");
    	File backgroundImageFile = new File("./IMG_6808.jpg");
    	Image backgroundImage2 = new Image(backgroundImageFile.toURI().toString());
    	 Pane root = new Pane();
         BackgroundImage background = new BackgroundImage(
                 backgroundImage2,
                 BackgroundRepeat.NO_REPEAT,
                 BackgroundRepeat.NO_REPEAT,
                 null,
                 new BackgroundSize(
                     1.0, 1.0, true, true, false, false
                 )
             );
        root.setBackground(new Background(background));
    	HBox hbox = new HBox(20);
    	 Button playAgainButton = new Button("Main Menu");
    	 playAgainButton.setStyle("-fx-background-color: transparent; -fx-font-size: 50px; -fx-text-fill: white; -fx-font-family: 'Abadi';");
    	 playAgainButton.setMinWidth(100);
    	 playAgainButton.setMinHeight(60);
    	 playAgainButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
         playAgainButton.setOnAction(event -> {
        	 stage.close();
        	 primaryStage.close();
        	 Platform.runLater(() -> {
                 try {
                     start(new Stage());
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             });
         });
         Button quitButton = new Button("Quit");
         quitButton.setStyle("-fx-background-color: transparent; -fx-font-size: 50px; -fx-text-fill: white; -fx-font-family: 'Abadi';");
         quitButton.setMinWidth(100);
         quitButton.setMinHeight(60);
         quitButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
         quitButton.setOnAction(event -> {
        	 
        	 Platform.exit();
        	 System.exit(0);
        });

    	hbox.getChildren().addAll(playAgainButton,quitButton);
    	vbox.getChildren().addAll(label,hbox);
    	root.getChildren().add(vbox);
    	 
        
         

    	Scene scene = new Scene(root);

    	
    	stage.setScene(scene);
    	stage.show();
    }
    @Override
	public void handle(MouseEvent mouseEvent) {
		if(((MouseEvent) mouseEvent).getButton()==MouseButton.PRIMARY){
            if(((MouseEvent) mouseEvent).getClickCount() == 2){
            	Game.startGame(Bat);
            	displayMap(this.primary);
            }
        }
		
	}
    public void selectHero(Stage primaryStage,Stack<Scene> sceneStack)  throws IOException  {
   
    	Game.loadHeroes("./Heroes.csv");
        String y="./HBFE8fN.png";  
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefWidth(primaryStage.getWidth());
        borderPane.setPrefHeight(primaryStage.getHeight());
        File ImageFile = new File("./FaYD-JkX0AYLRxj.png");
        Image image = new Image(ImageFile.toURI().toString());
        Pane root = new Pane();
        BackgroundImage background = new BackgroundImage(
        		image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                null,
                new BackgroundSize(
                    1.0, 1.0, true, true, false, false
                )
            );
        root.setBackground(new Background(background));
        HBox Fighters = new HBox(20);
        Label fighters = new Label("Fighters");
        fighters.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 25;");
        
        HBox Medics = new HBox(20);
        Label medics = new Label("Medics");
        medics.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 25;");
        
        HBox Explorers = new HBox(20);
        Label explorers = new Label("Explorers");
        explorers.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 25;");
        
        VBox Left = new VBox(40);
        borderPane.setLeft(Left);
        Left.setAlignment(Pos.CENTER_LEFT);
        
      
        for(Hero hero : Game.availableHeroes) {
        	Button button = new Button();
        	button.setPrefWidth(100);
        	button.setPrefHeight(100);
        	
        	switch (hero.getName()){
    		case "Joel Miller":
    			y= "./418a4cb33049217f82b65a9059f77b46.png";
    			button.setOnAction(e -> {
    				File backgroundImageFile1 = new File("./My project (1).png");
    		         Image image2 = new Image(backgroundImageFile1.toURI().toString());
    		          ImageView img = new ImageView(image2);  
    		          Label label = new Label("Name: " + hero.getName() + "\n" + "Type: Fighter \n"
    		        		  +"Max Hp: " + hero.getMaxHp() + "\n"
    		        		  +"Attack Damage: "  + hero.getAttackDmg() + "\n" + "Max Actions: " + hero.getMaxActions());
    		         label.setStyle("-fx-background-color: transparent; -fx-font-size: 35; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'");
    		          borderPane.setCenter(img);
    		          borderPane.setRight(label);
    		          borderPane.setAlignment(label, Pos.CENTER_RIGHT);
    		          label.setPadding(new Insets(0, 20, 0, 0));
    		        Bat = hero;
    		        primary=primaryStage;
            		 });
    			button.setOnMouseClicked(this); 
    			break;
    		case "Ellie Williams":
    		    y = "./ellie_large.png";

    		    button.setOnAction(e -> {
    		        File backgroundImageFile1 = new File("./My project.png");
    		          Image image2 = new Image(backgroundImageFile1.toURI().toString());
    		          ImageView img = new ImageView(image2);  
    		          Label label = new Label("Name: " + hero.getName() + "\n" + "Type: Medic \n"
    		        		  +"Max Hp: " + hero.getMaxHp() + "\n"
    		        		  +"Attack Damage: "  + hero.getAttackDmg() + "\n" + "Max Actions: " + hero.getMaxActions());
    		          label.setStyle("-fx-background-color: transparent; -fx-font-size: 35; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'");    		          
    		          borderPane.setCenter(img);
    		          borderPane.setAlignment(label, Pos.CENTER_RIGHT);
    		          label.setPadding(new Insets(0, 20, 0, 0));
    		          borderPane.setRight(label);     
    		          Bat = hero;
    		          primary=primaryStage;
       		 });
			button.setOnMouseClicked(this); 
    		    break;

    		case "Tess":
    			y="./250.png";
    			button.setOnAction(e -> {
    				File backgroundImageFile1 = new File("./Tess.png");
    				Image image2 = new Image(backgroundImageFile1.toURI().toString());
  		          	ImageView img = new ImageView(image2); 
    		          Label label = new Label("Name: " + hero.getName() + "\n" + "Type: Explorer \n"
    		        		  +"Max Hp: " + hero.getMaxHp() + "\n"
    		        		  +"Attack Damage: "  + hero.getAttackDmg() + "\n" + "Max Actions: " + hero.getMaxActions());
    		          label.setStyle("-fx-background-color: transparent; -fx-font-size: 35; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'");    		          
    		          borderPane.setCenter(img);
    		          borderPane.setAlignment(label, Pos.CENTER_RIGHT);
    		          label.setPadding(new Insets(0, 20, 0, 0));
    		          borderPane.setRight(label);
    		          Bat = hero;
    		          primary=primaryStage;
       		 });
			button.setOnMouseClicked(this); 
    			break;
    		case "Riley Abel":
    			y="./latest.png";
    			button.setOnAction(e -> {
    				File backgroundImageFile1 = new File("./Riley.png");
    				Image image2 = new Image(backgroundImageFile1.toURI().toString());
  		          ImageView img = new ImageView(image2); 
    		          Label label = new Label("Name: " + hero.getName() + "\n" + "Type: Explorer \n"
    		        		  +"Max Hp: " + hero.getMaxHp() + "\n"
    		        		  +"Attack Damage: "  + hero.getAttackDmg() + "\n" + "Max Actions: " + hero.getMaxActions());
    		          label.setStyle("-fx-background-color: transparent; -fx-font-size: 35; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'");    		          
    		          borderPane.setCenter(img);
    		          borderPane.setAlignment(label, Pos.CENTER_RIGHT);
    		          label.setPadding(new Insets(0, 20, 0, 0));
    		          borderPane.setRight(label);
    		          Bat = hero;
    		          primary=primaryStage;
       		 });
			button.setOnMouseClicked(this); 
    			break;
    		case "Tommy Miller":
    			y="./9kk.png";
    			button.setOnAction(e -> {
    				File backgroundImageFile1 = new File("./Tommy.png");
    				Image image2 = new Image(backgroundImageFile1.toURI().toString());
  		          ImageView img = new ImageView(image2); 
    		          Label label = new Label("Name: " + hero.getName() + "\n" + "Type: Explorer \n"
    		        		  +"Max Hp: " + hero.getMaxHp() + "\n"
    		        		  +"Attack Damage: "  + hero.getAttackDmg() + "\n" + "Max Actions: " + hero.getMaxActions());
    		          label.setStyle("-fx-background-color: transparent; -fx-font-size: 35; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'");    		          
    		          borderPane.setCenter(img);
    		          borderPane.setAlignment(label, Pos.CENTER_RIGHT);
    		          label.setPadding(new Insets(0, 20, 0, 0));
    		          borderPane.setRight(label);
    		          Bat = hero;
    		          primary=primaryStage;
       		 });
			button.setOnMouseClicked(this); 
    			break;
    		case "Bill":
    			y="./9k.png";
    			button.setOnAction(e -> {
    				File backgroundImageFile1 = new File("./Bill.png");
    				Image image2 = new Image(backgroundImageFile1.toURI().toString());
  		          ImageView img = new ImageView(image2); 
    		          Label label = new Label("Name: " + hero.getName() + "\n" + "Type: Medic \n"
    		        		  +"Max Hp: " + hero.getMaxHp() + "\n"
    		        		  +"Attack Damage: " + hero.getAttackDmg() + "\n" + "Max Actions: " + hero.getMaxActions());
    		          label.setStyle("-fx-background-color: transparent; -fx-font-size: 35; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'");    		          
    		          borderPane.setCenter(img);
    		          borderPane.setAlignment(label, Pos.CENTER_RIGHT);
    		          label.setPadding(new Insets(0, 20, 0, 0));
    		          borderPane.setRight(label);
    		          Bat = hero;
    		          primary=primaryStage;
       		 });
			button.setOnMouseClicked(this); 
    			break;
    		case "David":
    			y="./Terry-Crews-Expendables-750x400.jpg";
    			button.setOnAction(e -> {
    				File backgroundImageFile1 = new File("./david.png");
    				Image image2 = new Image(backgroundImageFile1.toURI().toString());
  		          ImageView img = new ImageView(image2); 
    		          Label label = new Label("Name: " + hero.getName() + "\n" + "Type: Fighter \n"
    		        		  +"Max Hp: " + hero.getMaxHp() + "\n"
    		        		  +"Attack Damage: "  + hero.getAttackDmg() + "\n" + "Max Actions: " + hero.getMaxActions());
    		          label.setStyle("-fx-background-color: transparent; -fx-font-size: 35; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'");    		          
    		          borderPane.setCenter(img);
    		          borderPane.setAlignment(label, Pos.CENTER_RIGHT);
    		          label.setPadding(new Insets(0, 20, 0, 0));
    		          borderPane.setRight(label);
    		          Bat = hero;
    		          primary=primaryStage;
       		 });
			button.setOnMouseClicked(this); 
    			break;
    		case "Henry Burell":
    			y="./images.jpg";
    			button.setOnAction(e -> {
    				File backgroundImageFile1 = new File("./latest444.png");
    				Image image2 = new Image(backgroundImageFile1.toURI().toString());
  		          ImageView img = new ImageView(image2); 
    		          Label label = new Label("Name: " + hero.getName() + "\n" + "Type: Medic \n"
    		        		  +"Max Hp: " + hero.getMaxHp()  + "\n"
    		        		  +"Attack Damage: "  + hero.getAttackDmg() + "\n" + "Max Actions: " + hero.getMaxActions());
    		          label.setStyle("-fx-background-color: transparent; -fx-font-size: 35; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'");
    		          borderPane.setCenter(img);
    		          borderPane.setAlignment(label, Pos.CENTER_RIGHT);
    		          label.setPadding(new Insets(0, 20, 0, 0));
    		          borderPane.setRight(label);
    		          Bat = hero;
    		          primary=primaryStage;
       		 });
			button.setOnMouseClicked(this); 
    			break;
    		}
        	File backgroundImageFile1 = new File(y);
	        Image image11 = new Image(backgroundImageFile1.toURI().toString());
	        BackgroundImage backgroundImage1 = new BackgroundImage(
                    image11,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(100, 100, false, false, false, false));
            	 button.setBackground(new Background(backgroundImage1));

        	if(hero instanceof Fighter) {
        		Fighters.getChildren().add(button);
        	}
        	else if(hero instanceof Medic) {
        		Medics.getChildren().add(button);
        	}
        	else if(hero instanceof Explorer) {
        		Explorers.getChildren().add(button);
            }
        	
    }
        HBox topLeftBox = new HBox(10);
        topLeftBox.setAlignment(Pos.TOP_LEFT);
        Button backButton = new Button(">Back");
        backButton.setStyle("-fx-background-color: transparent; -fx-font-size: 40; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold';");
        backButton.setMinWidth(200);
        backButton.setMinHeight(60);
        backButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
        backButton.setOnAction(event -> {
        	Scene previousScene = sceneStack.pop();
            primaryStage.setScene(previousScene);
            Game.heroes.clear();
            Game.availableHeroes.clear();
            
        });

        topLeftBox.getChildren().add(backButton);
        root.getChildren().add(borderPane);
        Left.getChildren().addAll(topLeftBox,fighters,Fighters,medics,Medics,explorers,Explorers);
        Scene scene = new Scene(root);   
        sceneStack.push(primaryStage.getScene());
        primaryStage.setScene(scene);
        primaryStage.setWidth(1650);
        primaryStage.setHeight(1000);
    	primaryStage.setResizable(false);
    }
    
    public void helpScene(Stage primaryStage,Stack<Scene> sceneStack) {
    	HBox topLeftBox = new HBox();
        topLeftBox.setAlignment(Pos.TOP_LEFT);

        Button backButton = new Button(">Back");
        backButton.setStyle("-fx-background-color: transparent; -fx-font-size: 40; -fx-text-fill: green; -fx-font-family: 'Arial Rounded MT Bold';");
        backButton.setMinWidth(200);
        backButton.setMinHeight(60);
        backButton.setContentDisplay(ContentDisplay.TEXT_ONLY);
        backButton.setOnAction(event -> {
        	Scene previousScene = sceneStack.pop();
            primaryStage.setScene(previousScene);
        });

        topLeftBox.getChildren().add(backButton);
    	
    	VBox vbox = new VBox();
    	vbox.setAlignment(Pos.TOP_CENTER);

        
        Label headerLabel = new Label("Help");
        headerLabel.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-font-size: 60; -fx-text-fill: red; -fx-font-family: 'Arial Rounded MT Bold'");
        
        Label infoLabel = new Label("The Last of Us: Legacy is a single player survival game set in a zombie apocalyptic world. \n"
        		+ "The game is conducted in a turn based manner, in which each player character receives a specific \n"
        		+ "number of action points per turn, which they can use to move, attack or cure zombies, or use \n"
        		+ "special actions. \n"
        		+ "The player starts the game controlling only one hero, but can gain additional heroes by curing \n"
        		+ "zombies. The objective of the game for the player is to survive as long as it takes in order to \n"
        		+ "cure a sufficient number of zombies enough to build a community to survive the apocalypse. \n"
        		+ "\n"
        		+ "Hero Selection: Press single click to show hero information, double click on the hero to start the game. \n" 
        		+ "Cycle through heroes : Click on the hero's icon to play with him. \n"+ "\n"
        		+ "Each hero type has a unique action they can add to the player’s team: \n"
        		+ "      -Explorer: Allows the player to be able to see the entirety of the map for 1 turn whenever \n"
        		+ "        		   supply is used.\n"
        		+ "       -Medic: Can heal and restore health to other heroes or themselves, each process of healing \n"
        		+ "        	    uses 1 supply. \n"
        		+ "       -Fighter: Can attack as many times in a turn without costing action points, for 1 turn \n"
        		+ "        		  whenever a supply is used. \n"
        		+ "\n"
        		+ "Possible actions that can be done by a character: \n"
        		+ "    -Move : By using the movement keys \n"
        		+ "    -Attack a zombie : Click on a near by zombie and attack him \n"
        		+ "    -Cure a zombie : Click on a near by zombie and cure him \n"
        		+ "    -Use their class dependant unique action \n"
        		+ "    -Choose target: Click on a visible character cell in the map \n"
        		+ "    -Select Hero: Click on the picture of the hero which is next to his information in the game \n"
        			);
        infoLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 22; -fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'");
 
        vbox.getChildren().addAll(topLeftBox,headerLabel, infoLabel);

        File backgroundImageFile = new File("./HelpScreen.jpg");
        Image backgroundImage = new Image(backgroundImageFile.toURI().toString());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(primaryStage.heightProperty());
        StackPane stackpane = new StackPane();
        stackpane.getChildren().addAll(backgroundImageView,vbox);
        sceneStack.push(primaryStage.getScene());
        primaryStage.setScene(new Scene(stackpane));
    	
    }
}