// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

//package com.google.codeu.codingchallenge;

import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;

final class MyJSONParser implements JSONParser {

  @Override
  public MyJSON parse(String in) throws IOException {

    Hashtable allData = new Hashtable();
    //Will need to set things in this object

    //so instead of putting values into a hashtable,
    //use the functions you just wrote
	in.trim();

    if(in.substring(0,1).equals("{")) {
        parse(in.substring(1));   
    } 
    else if(in.substring(0,1).equals("}")) {
		MyJSON tempMJ = new MyJSON(allData); 
        return tempMJ;
    }
    else if (in.substring(0,1).equals("\"")) {
        String skipFirstQuote;
        int endQuote;
        String tempKey;

        skipFirstQuote = in.substring(1);
        endQuote = skipFirstQuote.indexOf("\"");
        tempKey = skipFirstQuote.substring(0,endQuote);

        if (skipFirstQuote.substring(endQuote,endQuote+1).equals(":")) {
            // If there is a semicolon, there is a value 
            // associated with that key
            //find the value and put it into your object
            if(skipFirstQuote.substring(endQuote+1, endQuote+2).equals("\"")){
                String skipSemi;
                int tempStartVal;
                String newIn;
                int tempEndVal;
                String tempValue;

                skipSemi = skipFirstQuote.substring(endQuote+2);
                tempStartVal = skipSemi.indexOf("\"");
                newIn = skipSemi.substring(tempStartVal);
                tempEndVal=skipSemi.indexOf("\"");
                tempValue = skipSemi.substring(tempStartVal, 1+tempEndVal);

                //put into JSON object
                allData.put(tempKey,tempValue);
            }
            else if(in.substring(endQuote+1, endQuote+2).equals("{")){
                // If the symbol after the semicolon is a "{"
                // then there is another hashtable after the key
                // but we still add to the hashtable,
                // we just need to recurse on the hashtable
                String newIn;
                int endBraket;
                String tempValHT;

                newIn = skipFirstQuote.substring(endQuote+2);
                endBraket = newIn.indexOf("}");
                tempValHT = newIn.substring(0,endBraket);
                //first we parse the hashtable so that
                //it passes the parsed hashtable in and 
                //not a string representing the JSON
                //then we put it in the object
                Hashtable parsedHT = new Hashtable();
                MyJSON tempMJ;
                tempMJ = parse(in.substring(endQuote+2,endBraket));
                allData.put(tempKey, tempMJ);
                
            }
        }
        else if(in.substring(0,1).equals(",")) {
            //keep finding pair values and putting them into the object
            //until you hit a curly brace
            //keep checking for strings 
            String newStringIn = in.substring(2);
            String nextCharacter = in.substring(2,3);
            do{
                // CHECK IN HERE TO MAKE SURE THE VALUE OF NEXTCHARACTER
                //GETS UPDATED
                int startQuote;

                startQuote = newStringIn.indexOf("\"");
                skipFirstQuote = newStringIn.substring(startQuote);
                endQuote = skipFirstQuote.indexOf("\"");
                tempKey =newStringIn.substring(startQuote,startQuote+endQuote);

                if (newStringIn.substring(endQuote,endQuote+1).equals(":")) {
                    // If there is a semicolon, there is a value 
                    // associated with that key
                    //find the value and put it into your object
                    if(newStringIn.substring(endQuote+1,endQuote+2).equals("\"")){
                        String newIn;
                        int tempEndVal;
                        String tempValue;

                        newIn = newStringIn.substring(endQuote+2);
                        tempEndVal = newIn.indexOf("\"");
                        tempValue = newIn.substring(0,tempEndVal);

                        //add to JSON object
                        allData.put(tempKey,tempValue);
                    }
                    else if(newStringIn.substring(endQuote+1, endQuote+2).equals("{")){
                        // If the symbol after the semicolon is a "{"
                        // then there is another hashtable after the key
                        // but we still add to the object
                        String newIn;
                        int endBraket;
                        String tempValHT;

                        newIn = newStringIn.substring(endQuote+2);
                        endBraket = newIn.indexOf("}");
                        tempValHT = newIn.substring(0,endBraket);
						
                        MyJSON tempMJ;
                        tempMJ = parse(tempValHT);
                        allData.put(tempKey, tempMJ);
                    }
                }

            } while (!nextCharacter.equals("}"));
        } 
        else{
            //might want to continue the loop, maybe have a while loop
            //so if on eline is messed up but keep going??
            IOException e = new IOException();
            throw e;
        }   
    }
    MyJSON MJ = new MyJSON(allData);
    return MJ;
  }
}

  /// include a thing with tests and the output and screen shots???
  //make sure if works and then start cleaning up
  //System.out.println("");
