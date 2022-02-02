#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define FIREBASE_AUTH "SrE69ZPkiFyVuFdyM7FQI9VlQWPmYmxtivRWcWoN"
#define FIREBASE_HOST "qrlock-project.firebaseio.com"

#define WiFi_SSID "qrlock"
#define WiFi_PASS "qraplikasi"

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  //connect WiFi
  WiFi.begin(WiFi_SSID, WiFi_PASS);
  
  while(WiFi.status() != WL_CONNECTED){
    Serial.print(".");
    delay(500);
  }
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {
  
  String stats1 = Firebase.getString("Pintu/qrpintu1/Status");
  String stats2 = Firebase.getString("Pintu/qrpintu2/Status");
  String stats3 = Firebase.getString("Pintu/qrpintu3/Status");
  String stats4 = Firebase.getString("Pintu/qrpintu4/Status");
  String stats5 = Firebase.getString("Pintu/qrpintu5/Status");
  String stats6 = Firebase.getString("Pintu/qrpintu6/Status");
  String stats7 = Firebase.getString("Pintu/qrpintu7/Status");
//  String stats8 = Firebase.getString("Pintu/qrpintu8/Status");
//  String stats9 = Firebase.getString("Pintu/qrpintu9/Status");
//  String stats10 = Firebase.getString("Pintu/qrpintu10/Status");
//  
  if(stats1=="Unlocked"){
    Serial.println("!");
    Firebase.setString("Pintu/qrpintu1/feedback","true");
  }
    if(stats2=="Unlocked"){
    Serial.println("@");
    Firebase.setString("Pintu/qrpintu2/feedback","true");
  }
    if(stats3=="Unlocked"){
    Serial.println("#");
    Firebase.setString("Pintu/qrpintu3/feedback","true");
  }
    if(stats4=="Unlocked"){
    Serial.println("$");
    Firebase.setString("Pintu/qrpintu4/feedback","true");
  }
    if(stats5=="Unlocked"){
    Serial.println("%");
    Firebase.setString("Pintu/qrpintu5/feedback","true");
  }
    if(stats6=="Unlocked"){
    Serial.println("^");
    Firebase.setString("Pintu/qrpintu6/feedback","true");
  }
    if(stats7=="Unlocked"){
    Serial.println("&");
    Firebase.setString("Pintu/qrpintu7/feedback","true");
  }
//    if((stats8=="Unlocked")&&(feed8 == "false")){
//    Serial.println("*");
//    Firebase.setString("Pintu/qrpintu8/feedback","true");
//  }
//    if((stats9=="Unlocked")&&(feed9 == "false")){
//    Serial.println("(");
//    Firebase.setString("Pintu/qrpintu9/feedback","true");
//  }
//  if((stats10=="Unlocked")&&(feed10 == "false")){
//    Serial.println(")");
//    Firebase.setString("Pintu/qrpintu10/feedback","true");
//  }
}
