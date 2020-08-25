import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class BattleshipGame extends Application {
	int shipNum = 1;
	int[][] shipCoor1 = new int[2][3];
	int[][] shipCoor2 = new int[2][3];
	boolean toRootRemoved = false;
	String[][] p1Board = blankBoard();
	String[][] p2Board = blankBoard();
	int x = 0;
	int y = 0;
	int oneWins = 0;
	int twoWins = 0;
	Label winner = new Label("");


	public void start(Stage stage) {
		stage.setTitle("Battleship");

		Button newGame = new Button("New Game");
		newGame.setFont(Font.font("Goudy Stout", 15));
		newGame.setTextFill(Color.SKYBLUE);
		Label welcome = new Label("BATTLESHIP");
		welcome.setFont(Font.font("Goudy Stout", 50));
		welcome.setTextFill(Color.SKYBLUE);

		HBox justNew = new HBox();
		justNew.setAlignment(Pos.CENTER);
		justNew.getChildren().add(newGame);
		VBox mid = new VBox();
		mid.setAlignment(Pos.CENTER);
		mid.getChildren().addAll(welcome, justNew);

		Button enter1 = new Button("P1: Enter Ships");
		enter1.setFont(Font.font("Goudy Stout", 15));
		enter1.setTextFill(Color.SKYBLUE);
		enter1.setVisible(false);
		Label ship1 = new Label("Enter Ship #");
		Label num1 = new Label("1");
		Label row = new Label("Row");
		Label col = new Label("Col");
		TextField rowIn = new TextField();
		TextField colIn = new TextField();
		Button submitEach = new Button("Submit");
		ship1.setFont(Font.font("Goudy Stout", 12));
		ship1.setTextFill(Color.SKYBLUE);
		num1.setFont(Font.font("Goudy Stout", 12));
		num1.setTextFill(Color.SKYBLUE);
		row.setFont(Font.font("Goudy Stout", 12));
		row.setTextFill(Color.SKYBLUE);
		col.setFont(Font.font("Goudy Stout", 12));
		col.setTextFill(Color.SKYBLUE);
		submitEach.setFont(Font.font("Goudy Stout", 15));
		submitEach.setTextFill(Color.SKYBLUE);


		GridPane gp1 = new GridPane();
		gp1.add(ship1,0,0,1,1);
		gp1.add(num1,1,0,1,1);
		gp1.add(row,0,1,1,1);
		gp1.add(col,0,2,1,1);
		gp1.add(rowIn,1,1,1,1);
		gp1.add(colIn,1,2,1,1);
		gp1.add(submitEach,0,3,2,2);
		gp1.setVisible(false);

		enter1.setOnAction(event -> {
			gp1.setVisible(true);
			submitEach.setDisable(false);
			num1.setText("1");
		});
		submitEach.setOnAction(event -> {
			try {
				int rowVal = Integer.parseInt(rowIn.getCharacters().toString());
				int colVal = Integer.parseInt(colIn.getCharacters().toString());
				if (notInBound(rowVal,colVal)) {
					throw new InputException("You have entered invalid coordinates. Please enter either 1, 2, 3, or 4.");
				}
				for (int i=1; i <=3 ; i++) {
					if (shipNum == i) {
						shipCoor1[0][i-1] = rowVal;
						shipCoor1[1][i-1] = colVal;
					}
				}
				rowIn.clear();
				colIn.clear();
				shipNum++;
				if (shipNum > 3) {
					submitEach.setDisable(true);
					enter1.setDisable(true);
					shipNum = 1;
					p1Board[shipCoor1[0][0]-1][shipCoor1[1][0]-1] = "X";
					p1Board[shipCoor1[0][1]-1][shipCoor1[1][1]-1] = "X";
					p1Board[shipCoor1[0][2]-1][shipCoor1[1][2]-1] = "X";
				} else {
					String set = "" + shipNum;
					num1.setText(set);
				}
			} catch (InputException e) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Input Error");
				a.setHeaderText(e.getMessage());
				a.showAndWait();
				rowIn.clear();
				colIn.clear();
			} catch (Exception e) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Input Error");
				a.setHeaderText("You have left the row or column blank. Please enter either 1, 2, 3, or 4.");
				a.showAndWait();
				rowIn.clear();
				colIn.clear();
			}
		});

		VBox arr1 = new VBox();
		arr1.setAlignment(Pos.CENTER);
		arr1.setSpacing(25);
		arr1.getChildren().addAll(enter1,gp1);

		Button enter2 = new Button("P2: Enter Ships");
		enter2.setFont(Font.font("Goudy Stout", 15));
		enter2.setTextFill(Color.SKYBLUE);
		enter2.setVisible(false);
		Label ship2 = new Label("enter ship #");
		Label num2 = new Label("1");
		Label row2 = new Label("row");
		Label col2 = new Label("col");
		TextField rowIn2 = new TextField();
		TextField colIn2 = new TextField();
		Button submitEach2 = new Button("Submit");
		ship2.setFont(Font.font("Goudy Stout", 12));
		ship2.setTextFill(Color.SKYBLUE);
		num2.setFont(Font.font("Goudy Stout", 12));
		num2.setTextFill(Color.SKYBLUE);
		row2.setFont(Font.font("Goudy Stout", 12));
		row2.setTextFill(Color.SKYBLUE);
		col2.setFont(Font.font("Goudy Stout", 12));
		col2.setTextFill(Color.SKYBLUE);
		submitEach2.setFont(Font.font("Goudy Stout", 15));
		submitEach2.setTextFill(Color.SKYBLUE);

		GridPane gp2 = new GridPane();
		gp2.add(ship2,0,0,1,1);
		gp2.add(num2,1,0,1,1);
		gp2.add(row2,0,1,1,1);
		gp2.add(col2,0,2,1,1);
		gp2.add(rowIn2,1,1,1,1);
		gp2.add(colIn2,1,2,1,1);
		gp2.add(submitEach2,0,3,2,2);
		gp2.setVisible(false);

		enter2.setOnAction(event -> {
			gp2.setVisible(true);
			num2.setText("1");
			submitEach2.setDisable(false);
		});

		VBox arr2 = new VBox();
		arr2.setAlignment(Pos.CENTER);
		arr2.setSpacing(25);
		arr2.getChildren().addAll(enter2,gp2);

		HBox toRoot = new HBox();
		toRoot.setAlignment(Pos.CENTER);
		toRoot.setSpacing(100);
		toRoot.getChildren().addAll(arr1,arr2);

		VBox root = new VBox();
		root.setSpacing(50);
		root.getChildren().addAll(mid,toRoot);


		Label oppHeader1 = new Label("Opponent Tracker Board:");
		oppHeader1.setFont(Font.font("Goudy Stout", 12));
		oppHeader1.setTextFill(Color.SKYBLUE);
		GridPane p1Opp = new GridPane();
		p1Opp.setVgap(0);
		p1Opp.setHgap(0);
		Button one1a = new Button();
		Button one1b = new Button();
		Button one1c = new Button();
		Button one1d = new Button();
		Button one2a = new Button();
		Button one2b = new Button();
		Button one2c = new Button();
		Button one2d = new Button();
		Button one3a = new Button();
		Button one3b = new Button();
		Button one3c = new Button();
		Button one3d = new Button();
		Button one4a = new Button();
		Button one4b = new Button();
		Button one4c = new Button();
		Button one4d = new Button();
		Button[][] arrOneButtons = {{one1a, one1b, one1c, one1d}, {one2a, one2b, one2c, one2d}, {one3a, one3b, one3c, one3d}, {one4a, one4b, one4c, one4d}};
		p1Opp.add(one1a,0,0);
		p1Opp.add(one1b,1,0);
		p1Opp.add(one1c,2,0);
		p1Opp.add(one1d,3,0);
		p1Opp.add(one2a,0,1);
		p1Opp.add(one2b,1,1);
		p1Opp.add(one2c,2,1);
		p1Opp.add(one2d,3,1);
		p1Opp.add(one3a,0,2);
		p1Opp.add(one3b,1,2);
		p1Opp.add(one3c,2,2);
		p1Opp.add(one3d,3,2);
		p1Opp.add(one4a,0,3);
		p1Opp.add(one4b,1,3);
		p1Opp.add(one4c,2,3);
		p1Opp.add(one4d,3,3);
		for (x=0; x<4; x++) {
			for (y=0; y<4; y++) {
				arrOneButtons[x][y].setPrefSize(40,40);
				arrOneButtons[x][y].setStyle("-fx-focus-color: transparent;");
			}
		}

		Label oppHeader2 = new Label("Opponent Tracker Board:");
		oppHeader2.setFont(Font.font("Goudy Stout", 12));
		oppHeader2.setTextFill(Color.SKYBLUE);
		GridPane p2Opp = new GridPane();
		p2Opp.setVgap(0);
		p2Opp.setHgap(0);
		Button two1a = new Button();
		Button two1b = new Button();
		Button two1c = new Button();
		Button two1d = new Button();
		Button two2a = new Button();
		Button two2b = new Button();
		Button two2c = new Button();
		Button two2d = new Button();
		Button two3a = new Button();
		Button two3b = new Button();
		Button two3c = new Button();
		Button two3d = new Button();
		Button two4a = new Button();
		Button two4b = new Button();
		Button two4c = new Button();
		Button two4d = new Button();

		Button[][] arrTwoButtons = {{two1a, two1b, two1c, two1d}, {two2a, two2b, two2c, two2d}, {two3a, two3b, two3c, two3d}, {two4a, two4b, two4c, two4d}};

		one1a.setOnAction(event -> {
			one1a.setText(inButton(0,0,1));
			one1a.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one1b.setOnAction(event -> {
			one1b.setText(inButton(0,1,1));
			one1b.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one1c.setOnAction(event -> {
			one1c.setText(inButton(0,2,1));
			one1c.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one1d.setOnAction(event -> {
			one1d.setText(inButton(0,3,1));
			one1d.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one2a.setOnAction(event -> {
			one2a.setText(inButton(1,0,1));
			one2a.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one2b.setOnAction(event -> {
			one2b.setText(inButton(1,1,1));
			one2b.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one2c.setOnAction(event -> {
			one2c.setText(inButton(1,2,1));
			one2c.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one2d.setOnAction(event -> {
			one2d.setText(inButton(1,3,1));
			one2d.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one3a.setOnAction(event -> {
			one3a.setText(inButton(2,0,1));
			one3a.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one3b.setOnAction(event -> {
			one3b.setText(inButton(2,1,1));
			one3b.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one3c.setOnAction(event -> {
			one3c.setText(inButton(2,2,1));
			one3c.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one3d.setOnAction(event -> {
			one3d.setText(inButton(2,3,1));
			one3d.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one4a.setOnAction(event -> {
			one4a.setText(inButton(3,0,1));
			one4a.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one4b.setOnAction(event -> {
			one4b.setText(inButton(3,1,1));
			one4b.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one4c.setOnAction(event -> {
			one4c.setText(inButton(3,2,1));
			one4c.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		one4d.setOnAction(event -> {
			one4d.setText(inButton(3,3,1));
			one4d.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 1 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two1a.setOnAction(event -> {
			two1a.setText(inButton(0,0,2));
			two1a.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two1b.setOnAction(event -> {
			two1b.setText(inButton(0,1,2));
			two1b.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}

		});
		two1c.setOnAction(event -> {
			two1c.setText(inButton(0,2,2));
			two1c.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two1d.setOnAction(event -> {
			two1d.setText(inButton(0,3,2));
			two1d.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two2a.setOnAction(event -> {
			two2a.setText(inButton(1,0,2));
			two2a.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two2b.setOnAction(event -> {
			two2b.setText(inButton(1,1,2));
			two2b.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two2c.setOnAction(event -> {
			two2c.setText(inButton(1,2,2));
			two2c.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two2d.setOnAction(event -> {
			two2d.setText(inButton(1,3,2));
			two2d.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two3a.setOnAction(event -> {
			two3a.setText(inButton(2,0,2));
			two3a.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two3b.setOnAction(event -> {
			two3b.setText(inButton(2,1,2));
			two3b.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two3c.setOnAction(event -> {
			two3c.setText(inButton(2,2,2));
			two3c.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two3d.setOnAction(event -> {
			two3d.setText(inButton(2,3,2));
			two3d.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two4a.setOnAction(event -> {
			two4a.setText(inButton(3,0,2));
			two4a.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two4b.setOnAction(event -> {
			two4b.setText(inButton(3,1,2));
			two4b.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two4c.setOnAction(event -> {
			two4c.setText(inButton(3,2,2));
			two4c.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});
		two4d.setOnAction(event -> {
			two4d.setText(inButton(3,3,2));
			two4d.setDisable(true);
			if (gameOver()) {
				setButton(true, arrOneButtons, arrTwoButtons);
				winner.setText("PLAYER 2 WINS!");
				winner.setFont(Font.font("Goudy Stout", 40));
				winner.setTextFill(Color.SKYBLUE);
			}
		});


		p2Opp.add(two1a,0,0);
		p2Opp.add(two1b,1,0);
		p2Opp.add(two1c,2,0);
		p2Opp.add(two1d,3,0);
		p2Opp.add(two2a,0,1);
		p2Opp.add(two2b,1,1);
		p2Opp.add(two2c,2,1);
		p2Opp.add(two2d,3,1);
		p2Opp.add(two3a,0,2);
		p2Opp.add(two3b,1,2);
		p2Opp.add(two3c,2,2);
		p2Opp.add(two3d,3,2);
		p2Opp.add(two4a,0,3);
		p2Opp.add(two4b,1,3);
		p2Opp.add(two4c,2,3);
		p2Opp.add(two4d,3,3);
		for (x=0; x<=3; x++) {
			for (y=0; y<=3; y++) {
				arrTwoButtons[x][y].setPrefSize(40,40);
				arrTwoButtons[x][y].setStyle("-fx-focus-color: transparent;");
			}
		}

		VBox oppBoard1 = new VBox();
		oppBoard1.setAlignment(Pos.CENTER);
		oppBoard1.setSpacing(25);
		oppBoard1.getChildren().addAll(oppHeader1, p1Opp);

		VBox oppBoard2 = new VBox();
		oppBoard2.setAlignment(Pos.CENTER);
		oppBoard2.setSpacing(25);
		oppBoard2.getChildren().addAll(oppHeader2, p2Opp);

		HBox oppTog = new HBox();
		oppTog.setAlignment(Pos.CENTER);
		oppTog.setSpacing(50);
		oppTog.getChildren().addAll(oppBoard1, oppBoard2);

		winner = new Label();
		HBox winBox = new HBox();
		winBox.setAlignment(Pos.CENTER);
		winBox.getChildren().add(winner);

		submitEach2.setOnAction(event -> {
			try {
				int rowVal = Integer.parseInt(rowIn2.getCharacters().toString());
				int colVal = Integer.parseInt(colIn2.getCharacters().toString());
				if (notInBound(rowVal,colVal)) {
					throw new InputException("You have entered invalid coordinates. Please enter either 1, 2, 3, or 4.");
				}
				for (int i=1; i <=3 ; i++) {
					if (shipNum == i) {
						shipCoor2[0][i-1] = rowVal;
						shipCoor2[1][i-1] = colVal;
					}
				}
				rowIn2.clear();
				colIn2.clear();
				shipNum++;
				if (shipNum > 3) {
					submitEach2.setDisable(true);
					enter2.setDisable(true);
					root.getChildren().remove(toRoot);
					toRootRemoved = true;
					p2Board[shipCoor2[0][0]-1][shipCoor2[1][0]-1] = "X";
					p2Board[shipCoor2[0][1]-1][shipCoor2[1][1]-1] = "X";
					p2Board[shipCoor2[0][2]-1][shipCoor2[1][2]-1] = "X";
					root.getChildren().addAll(oppTog, winBox);
				} else {
					String set = "" + shipNum;
					num2.setText(set);
				}
			} catch (InputException e) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Input Error");
				a.setHeaderText(e.getMessage());
				a.showAndWait();
				rowIn2.clear();
				colIn2.clear();
			} catch (Exception e) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Input Error");
				a.setHeaderText("You have left the row or column blank. Please enter either 1, 2, 3, or 4.");
				a.showAndWait();
				rowIn2.clear();
				colIn2.clear();
			}
		});


		newGame.setOnAction(event -> {
			if (toRootRemoved) {
				setButton(false, arrOneButtons, arrTwoButtons);
				root.getChildren().removeAll(oppTog, winBox);
				root.getChildren().add(toRoot);
			}
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Battleship Rules");
			alert.setHeaderText("Battleship Rules:\n1) Two players must participate\n2) You must enter 1, 2, 3, or 4 for the row and column" + 
			"\n3) No peeking when the other player enters their ship coordinates\n4) Players must have fun");
			alert.showAndWait();
			shipNum = 1;
			p1Board = blankBoard();
			p2Board = blankBoard();
			gp1.setVisible(false);
			gp2.setVisible(false);
			enter1.setVisible(true);
			enter1.setDisable(false);
			enter2.setVisible(true);
			enter2.setDisable(false);
			shipCoor1 = new int[2][3];
			shipCoor2 = new int[2][3];
		});

		StackPane toAdd = new StackPane();
		toAdd.setStyle("-fx-background-color: steelblue");
		toAdd.setPrefSize(800,600);
		toAdd.getChildren().add(root);

		Pane rootFinal = new Pane();
		rootFinal.getChildren().add(toAdd);

		Scene scene = new Scene(rootFinal,800,600);
		stage.setScene(scene);
		stage.show();
	}



	private static String[][] blankBoard() {
		String[][] result = new String[4][4];
		for (int x=0; x<4; x++) {
			for (int y=0; y<4; y++) {
				result[x][y] = "-";
			}
		}
		return result;
	}

	private static boolean notInBound(int num1, int num2) {
		return (num1 > 4 || num1 < 1 || num2 > 4 || num2 < 1);
	}

	private String inButton(int ind1, int ind2, int pNum) {
		if (pNum==1) {
			if (p2Board[ind1][ind2] == "X") {
				oneWins++;
				return "X";
			} else {
				return "O";
			}
		} else {
			if (p1Board[ind1][ind2] == "X") {
				twoWins++;
				return "X";
			} else {
				return "O";
			}
		}
	}

	private void setButton(boolean toSet, Button[][] arr1, Button[][] arr2) {
		for (x=0; x<4; x++) {
			for (y=0; y<4; y++) {
				arr1[x][y].setDisable(true);
			}
		}
		for (x=0; x<4; x++) {
			for (y=0; y<4; y++) {
				arr2[x][y].setDisable(true);
			}
		}
	}

	private boolean gameOver() {
		return (oneWins == 3) || (twoWins == 3);
	}
}