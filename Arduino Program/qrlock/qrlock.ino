#include <SoftwareSerial.h>
#include <Adafruit_PWMServoDriver.h>
#include <Wire.h>

SoftwareSerial mySerial(2,3);//rx,tx

Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();

#define SERVOMIN  150 // this is the 'minimum' pulse length count (out of 4096)
#define SERVOMAX  380 // this is the 'maximum' pulse length count (out of 4096)

#define FREQUENCY 60

unsigned long currentTime,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;

unsigned long delayTime = 6000;

uint8_t servonum = 0;


int speakerPin = 4;

boolean s1open = false;
boolean s2open = false;
boolean s3open = false;
boolean s4open = false;
boolean s5open = false;
boolean s6open = false;
boolean s7open = false;
boolean s8open = false;
boolean s9open = false;
boolean s10open = false;

uint8_t ports1 = 0;
uint8_t ports2 = 1;
uint8_t ports3 = 4;
uint8_t ports4 = 7;
uint8_t ports5 = 9;
uint8_t ports6 = 13;
uint8_t ports7 = 15;
uint8_t ports8 = 0;
uint8_t ports9 = 0;
uint8_t ports10 = 0;

int pins1 = 13;
int pins2 = 12;
int pins3 = 11;
int pins4 = 10;
int pins5 = 9;
int pins6 = 8;
int pins7 = 7;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  mySerial.begin(9600);
  
  pwm.begin();
  pwm.setPWMFreq(FREQUENCY);

  pinMode(pins1,INPUT_PULLUP);
  pinMode(pins2,INPUT_PULLUP);
  pinMode(pins3,INPUT_PULLUP);
  pinMode(pins4,INPUT_PULLUP);
  pinMode(pins5,INPUT_PULLUP);
  pinMode(pins6,INPUT_PULLUP);
  pinMode(pins7,INPUT_PULLUP);
  
  pinMode(speakerPin, OUTPUT);
}

void close(uint8_t num){
    tutupSempurna();
    for (uint16_t pulselen = SERVOMIN; pulselen < SERVOMAX; pulselen++) {
         pwm.setPWM(num, 0, pulselen);
    }
}

void open(uint8_t num){
  bukaSempurna();
  for (uint16_t pulselen = SERVOMAX; pulselen > SERVOMIN; pulselen--) {
      pwm.setPWM(num, 0, pulselen); 
  }
}
void belumNutup(){
  for(int i = 0; i<3; i++){
    tone(speakerPin,2300);
    delay(500);
    noTone(speakerPin);
    delay(100);
  } 
}

void tutupSempurna(){
  tone(speakerPin,2720);
  delay(100);
  tone(speakerPin,2500);
  delay(100);
  
  tone(speakerPin,2400);
  delay(100);
  noTone(speakerPin);
}
void bukaSempurna(){
  tone(speakerPin,2400);
  delay(100);
  tone(speakerPin,2500);
  delay(100);
  
  tone(speakerPin,2720);
  delay(100);
  noTone(speakerPin);
}
void loop() {
//delay(1000);
//
//  if(digitalRead(pins) == LOW){
//    s1open = false;
//  }
//  if(digitalRead(pins) == HIGH){
//    s1open = true;
//  }
//  
//  if(s1open == true && s1recent == true){
//   Serial.println("HI");
//    belumNutup();
//    s1open = true;
//   
//  }
//  
//  if(s1open == false && s1recent == true){
//    Serial.println("LO");
//    tutupSempurna();
//    s1open = false;
//    s1recent = false;
//  }
//  
//  Serial.println(s1open);
//  Serial.print("recent : ");
//  Serial.println(s1recent);
//                                                                                                                                                                                                                      
  currentTime = millis();
  
  //otomatis nutup pintu 1 dengan delay 5 detik
  if(s1open == true){
    if((currentTime - s1) >= delayTime){
      if(digitalRead(pins1) == LOW){
        close(ports1);
        s1open = false;
      }else{
        belumNutup();
      }
    }  
  }

  //otomatis nutup pintu 2 dengan delay 5 detik
  if(s2open == true){
    if((currentTime - s2) >= delayTime){
      if(digitalRead(pins2) == LOW){
        close(ports2);
        s2open = false;  
      }else{
        belumNutup();
      }
    }  
  }

  //otomatis nutup pintu 3 dengan delay 5 detik
  if(s3open == true){
    if((currentTime - s3) >= delayTime){
      if(digitalRead(pins3) == LOW){
        close(ports3);
        s3open = false;  
      }else{
        belumNutup();
      }
    }  
  }

  //otomatis nutup pintu 4 dengan delay 5 detik
  if(s4open == true){
    if((currentTime - s4) >= delayTime){
      if(digitalRead(pins4) == LOW){
        close(ports4);
        s4open = false;  
      }else{
        belumNutup();
      }
    }  
  }

  //otomatis nutup pintu 5 dengan delay 5 detik
  if(s5open == true){
    if((currentTime - s5) >= delayTime){
      if(digitalRead(pins5) == LOW){
        close(ports5);
        s5open = false;  
      }else{
        belumNutup();
      }
    }  
  }

  //otomatis nutup pintu 6 dengan delay 5 detik
  if(s6open == true){
    if((currentTime - s6) >= delayTime){
      if(digitalRead(pins6) == LOW){
        close(ports6);
        s6open = false;  
      }else{
        belumNutup();
      }
    }  
  }

  //otomatis nutup pintu 7 dengan delay 5 detik
  if(s7open == true){
    if((currentTime - s7) >= delayTime){
      if(digitalRead(pins7) == LOW){
        close(ports7);
        s7open = false;  
      }else{
        belumNutup();
      }
    }  
  }
  if(mySerial.available() > 0){
    String key = mySerial.readString();
    int keylen = key.length()/3;
    int pos = 1;
    int low = 0;

    Serial.println(key);
    for(int i = 0; i<keylen;i++){
      
      //control servo pintu1
      if((key.substring(low,low+1)=="!")){
        currentTime = millis();
        if(s1open == false){
          open(ports1);
          s1open = true;  
          s1=currentTime;
        }
      }
      
      //control servo pintu2
      if(key.substring(low,low+1)=="@"){
        currentTime = millis();
        if(s2open == false){
          open(ports2);
          s2open = true;  
          s2=currentTime;
        }
      }

      //control servo pintu3
      if(key.substring(low,low+1)=="#"){
        currentTime = millis();
        if(s3open == false){
          open(ports3);
          s3open = true;  
          s3=currentTime;
        }
      }

      //control servo pintu4
      if(key.substring(low,low+1)=="$"){
        currentTime = millis();
        if(s4open == false){
          open(ports4);
          s4open = true;  
          s4=currentTime;
        }
      }

      //control servo pintu5
      if(key.substring(low,low+1)=="%"){
        currentTime = millis();
        if(s5open == false){
          open(ports5);
          s5open = true;  
          s5=currentTime;
        }
      }

      //control servo pintu6
      if(key.substring(low,low+1)=="^"){
        currentTime = millis();
        if(s6open == false){
          open(ports6);
          s6open = true;  
          s6=currentTime;
        }
      }

      //control servo pintu7
      if(key.substring(low,low+1)=="&"){
        currentTime = millis();
        if(s7open == false){
          open(ports7);
          s7open = true;  
          s7=currentTime;
        }
      }

//      //control servo pintu8 -- NOTED-- PINTU RUSAK
//      if(key.substring(low,low+1)=="*"){
//        currentTime = millis();
//        if(s8open == false){
//          open(ports8);
//          s8open = true;  
//          s8=currentTime;
//        }
//        
//        if(((currentTime - s8) >= delayTime) && s8open == true){
//          close(ports8);
//          s8open = false;
//        }
//      }

//      //control servo pintu9 -- NOTED -- PINTU RUSAK
//      if(key.substring(low,low+1)=="("){
//        currentTime = millis();
//        if(s9open == false){
//          open(ports9);
//          s9open = true;  
//          s9=currentTime;
//        }
//        
//        if(((currentTime - s9) >= delayTime) && s9open == true){
//          close(ports9);
//          s9open = false;
//        }
//      }

//      //control servo pintu10 -- NOTED -- PINTU RUSAK
//      if(key.substring(low,low+1)==")"){
//        currentTime = millis();
//        if(s10open == false){
//          open(ports10);
//          s10open = true;  
//          s10=currentTime;
//        }
//        
//        if(((currentTime - s10) >= delayTime) && s10open == true){
//          close(ports10);
//          s10open = false;
//        }
//      }
      low = low+3;
    }
  }
}
