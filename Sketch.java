import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // Snow ball variables
  float[] circleY = new float[30];
  float[] circleX = new float[30];
  double snowBallSpeed = 1;
  boolean[] ballHideStatus = new boolean[30];

  // Player circle variables 
  float playerCircleX;
  float playerCircleY;
  boolean goUp = false;
  boolean goDown = false;
  boolean goLeft = false;
  boolean goRight = false;

  // Circle collision variables 
  float playerRadious = 20 / 2;
  float circleRadious = 25 / 2;
  float timeSince = 0;
  boolean isGreen = false;
  float greenCounter = 10;

  // Player lives variables
  int number_of_lives = 3;

  // Score variables
  int score = 0;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	// put your size call here
    size(400, 400);
  }

  /** 
   * Sets all ballHideStatus to false, sets the circleY and circleX 
   * values to somthing between the height and width respectively
   * and it intializes the player position
   */
  public void setup() {
    
    // Initalize all the ball hide statuses as false
    for (int i = 0; i < 30; i++){
      ballHideStatus[i] = false;
    }
    
    // Determine Y value for circles 
    for (int i = 0; i < circleY.length; i++){
      circleY[i] = random(height);
    }
    // Dertermine the X for the circles 
    for (int i = 0; i < circleX.length; i++){
      circleX[i] = random(width);
    }

    // Initalize player positions 
    playerCircleX = width/2;
    playerCircleY = height/2;
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
	
    // When all lives are lost...
    if (number_of_lives == 0){
      background(255, 255, 255);  
      fill(0);
      textSize(50);
      text("Total points", 100, height / 2 - 90);
      textSize(90);
      text(score, width/2 -50, height/2);
      playerCircleX = -500;
      playerCircleY = -500;
    }  
    else {
      background(50);
      snow();
    }

    // Player controls
    if (goUp){
      playerCircleY--;
    }
    if (goDown){
      playerCircleY +=2;
    }
    if (goLeft){
      playerCircleX--;
    }
    if (goRight){
      playerCircleX++;
    }

    // If no collision detected, player circle remains pink, otherwise make it green
    if (!isGreen){
      fill(50, 141, 168);
    }
    else{
      fill(134, 156, 146);
    }
    ellipse(playerCircleX, playerCircleY, 20, 20);

    // Collision detection
    for (int i = 0; i < 30; i++){
      if (isGreen == false && (dist(playerCircleX, playerCircleY, circleX[i], circleY[i]) <= playerRadious + circleRadious)){
        isGreen = true;
        number_of_lives--;
        timeSince = millis();      
        }

        if (isGreen && millis() >= timeSince + 1000){
            isGreen = false;
          }
      }
      
      
      // stop running if equal to 0
      if (number_of_lives != 0){
        playerScore();
      }

      lives();

    }

  /**
   * Draws 'snow balls' at randomized placed and resects the Y value of the 
   * ellipse if it goes off screen
   */
  public void snow(){
    for (int i = 0; i < circleY.length; i++){
      if (!ballHideStatus[i]){
        fill(255, 255, 255);
        ellipse(circleX[i], circleY[i], 25, 25);
      }
      
      circleY[i] += snowBallSpeed;
      if (circleY[i] > height){
        circleY[i] = 0;
      }
    }
  }

  /**
   * Does a specific function if a certain key is pressed
   */
  public void keyPressed(){
    if (key == 'w'){
      goUp = true;
    }
    if (key == 's'){
      goDown = true;
    }
    if (key == 'a'){
      goLeft = true;
    }
    if (key == 'd'){
      goRight = true;
    }
    if (keyCode == DOWN){
      snowBallSpeed += 1;
      if (snowBallSpeed > 6){
        snowBallSpeed = 6;
      }
    }
    if (keyCode == UP){
      snowBallSpeed = 0.5;
    }
  }

  /**
   * Does a certain function if a certain key is released
   */
  public void keyReleased(){
    if (keyCode == UP){
      snowBallSpeed = 1;
    }
    if (keyCode == DOWN){
      snowBallSpeed=1;
    }
    if (key == 'w'){
      goUp = false;
    }
    if (key == 's'){
      goDown = false;
    }
    if (key == 'a'){
      goLeft = false;
    }
    if (key == 'd'){
      goRight = false;
    }
  }

  /**
   * Displays the remaining player lives on the screen
   */
  public void lives(){
    int x = 375;
    for (int i = 0; i < number_of_lives; i++){
      fill(164, 192, 237);
      rect(x, 10, 20, 25);
      x -= 25;
    }
  }

  /**
   * Removes a ellipse from the players screen if it is pressed
   */
  public void mousePressed(){
    for (int i = 0; i < 30; i++){
      if (dist(mouseX, mouseY, circleX[i], circleY[i]) < circleRadious){
        ballHideStatus[i] = true;
      }
    }
  }

  /**
   * Displays the players score on the right side of the screen
   */
  public void playerScore(){
    score++;
    if (isGreen){
      score--;
    }
    text(score, 10,40);
  }


  }