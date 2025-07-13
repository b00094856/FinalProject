
#include <SoftwareSerial.h>
SoftwareSerial mySerial(8, 7); // RX, TX

// === Pin Configuration ===
const int redButton = 2;
const int blueButton = 3;
const int greenButton = 4;
const int yellowButton = 5;

const int redLED = 12;
const int blueLED = 13;
const int greenLED = 11;
const int yellowLED = 10;

const int buzzerpin = 9;

// Accelerometer analog pins
const int xpin = A3;  // x-axis
const int ypin = A2;  // y-axis
const int zpin = A1;  // z-axis

// === Variables ===
int red = 0, blue = 0, green = 0, yellow = 0;
String input = "";

void setup() {
  Serial.begin(57600);
  while (!Serial) { ; }

  mySerial.begin(9600);

  // Setup for the LEDs and buttons
  pinMode(redLED, OUTPUT);
  pinMode(blueLED, OUTPUT);
  pinMode(greenLED, OUTPUT);
  pinMode(yellowLED, OUTPUT);

  pinMode(redButton, INPUT);
  pinMode(blueButton, INPUT);
  pinMode(greenButton, INPUT);
  pinMode(yellowButton, INPUT);

  pinMode(buzzerpin, OUTPUT);
  digitalWrite(buzzerpin, HIGH); // Buzzer off initially
}

void loop() {
  if (mySerial.available()) {
    input = mySerial.readStringUntil('\n');
    input.trim();

    if (input == "READ_ACCEL") {
      int x = analogRead(xpin);
      int y = analogRead(ypin);
      int z = analogRead(zpin);

      char buffer[30];
      sprintf(buffer, "%d,%d,%d", x, y, z);
      Serial.println(buffer);
      mySerial.println(buffer);

      delay(50);
    }
    else if (input == "BUZZER_ON") {
      digitalWrite(buzzerpin, LOW);
      delay(500);
      digitalWrite(buzzerpin, HIGH);
    }
    else if (input == "LED:OFF") {
      digitalWrite(redLED, LOW);
      digitalWrite(greenLED, LOW);
      digitalWrite(blueLED, LOW);
      digitalWrite(yellowLED, LOW);
    }
    else if (input == "LED:ON") {
      digitalWrite(redLED, HIGH);
      digitalWrite(greenLED, HIGH);
      digitalWrite(blueLED, HIGH);
      digitalWrite(yellowLED, HIGH);
    }
    else if (input == "FLASH") {
      for (int i = 0; i < 3; i++) {
        digitalWrite(buzzerpin, LOW);
        digitalWrite(redLED, HIGH);
        digitalWrite(greenLED, HIGH);
        digitalWrite(blueLED, HIGH);
        digitalWrite(yellowLED, HIGH);
        delay(300);
        digitalWrite(buzzerpin, HIGH);
        digitalWrite(redLED, LOW);
        digitalWrite(greenLED, LOW);
        digitalWrite(blueLED, LOW);
        digitalWrite(yellowLED, LOW);
        delay(300);
      }
    }
    else if (input.startsWith("LED:")) {
      String color = input.substring(4);

      // Turn all LEDs off
      digitalWrite(redLED, LOW);
      digitalWrite(greenLED, LOW);
      digitalWrite(blueLED, LOW);
      digitalWrite(yellowLED, LOW);

      // Turn on the selected color
      if (color == "red") digitalWrite(redLED, HIGH);
      else if (color == "green") digitalWrite(greenLED, HIGH);
      else if (color == "blue") digitalWrite(blueLED, HIGH);
      else if (color == "yellow") digitalWrite(yellowLED, HIGH);
    }
  }

  // === Button Input Logic ===
  red = digitalRead(redButton);
  blue = digitalRead(blueButton);
  green = digitalRead(greenButton);
  yellow = digitalRead(yellowButton);

  if (red == HIGH) {
    Serial.println("red");
    mySerial.println("red");
    delay(200);
  }
  else if (blue == HIGH) {
    Serial.println("blue");
    mySerial.println("blue");
    delay(200);
  }
  else if (green == HIGH) {
    Serial.println("green");
    mySerial.println("green");
    delay(200);
  }
  else if (yellow == HIGH) {
    Serial.println("yellow");
    mySerial.println("yellow");
    delay(200);
  }
}
